import pandas as pd
import re, math
import psycopg2

conn = psycopg2.connect(
    dbname="bookrecommender", user="root", password="", host="localhost", port="5432"
)

cursor = conn.cursor()

authors = dict()
categories = dict()
editors = dict()
books = dict()

editors_id_counter = 0
books_id_counter = 0
authors_id_counter = 0
categories_id_counter = 0

def TreatAuthors(pAuthors):
    new_authors = pAuthors.replace("By ", "")
    new_authors = new_authors.replace("By", "")
    resultAuthors = re.split(', ', new_authors)

    global authors_id_counter, authors

    for x in resultAuthors:
        if x not in authors.values():
            authors[authors_id_counter] = x.strip();
            authors_id_counter += 1;

    result = []
    for x in resultAuthors:
        result.append(x.strip())
    return result

def TreatCategories(pCategories):
    if pCategories == "":
        return []
    new_categories = pCategories.replace("By ", "")
    resultCategories = re.split(', ', new_categories)

    global categories_id_counter, categories;

    for x in resultCategories:
        if x not in categories.values():
            categories[categories_id_counter] = x.strip();
            categories_id_counter += 1;
    
    result = []
    for x in resultCategories:
        result.append(x.strip())
    return result;

def get_key_from_value(dictionary, target_value):
    for key, value in dictionary.items():
        if value == target_value:
            return key
    return None  # Return None if value not found

def fromStringToId(array, theDict):
    result = []
    for x in array:
        result.append(get_key_from_value(theDict, x.strip()))
    return result;
# #################### MAIN ####################

def getIdOfPublisher(publisher):
    global editors, editors_id_counter;

    if publisher == "":
        return None

    for key, value in editors.items():
        if value == publisher:
            return key

    editors[editors_id_counter] = publisher
    editors_id_counter += 1
    return editors_id_counter - 1  


def MonthToNumber(month):
    month = month.lower()
    if month == "january":
        return 1
    elif month == "february":
        return 2
    elif month == "march":
        return 3
    elif month == "april":
        return 4
    elif month == "may":
        return 5
    elif month == "june":
        return 6
    elif month == "july":
        return 7
    elif month == "august":
        return 8
    elif month == "september":
        return 9
    elif month == "october":
        return 10
    elif month == "november":
        return 11
    elif month == "december":
        return 12
    else:
        return None

def ExecuteQueryAuthors(ids, values):
    global cursor
    for x in range(len(ids)):
        cursor.execute("""
        INSERT INTO authors (author_id, author_name) VALUES 
        (%s, %s) ON CONFLICT DO NOTHING;
        """,
        (ids[x], values[x])
        )

def ExecuteQueryCategories(values):
    global cursor
    for value in values:
        cursor.execute("""
        INSERT INTO categories (category_name) VALUES 
        (%s) ON CONFLICT DO NOTHING;
        """,
        (value,)
        )

def ExecuteQueryPublisher(value):
    global cursor
    publisherId = getIdOfPublisher(publisher)
    cursor.execute("""
    INSERT INTO editors (editor_id, editor_name) VALUES (%s, %s) ON CONFLICT DO NOTHING;
    """,
    (publisherId, value)
    )

def ExecuteQueryBook(bookId, title, editor, price, description, month, year):
    global cursor

    if editor == "":
        publisherId = None
    else:
        publisherId = getIdOfPublisher(editor)

    publish_date = f"{year}-{month}-01"

    if description == "":
        description = None

    cursor.execute("""
    INSERT INTO books (book_id, title, editor, price, description, publish_date) VALUES
    (%s, %s, %s, %s, %s, %s)
     ON CONFLICT DO NOTHING;
    """,
    (bookId, title, publisherId, price, description, publish_date)
    )
    
def ExecuteQueryJoinBooksAuthors(this_book_id, authorsIds):
    global cursor
    for x in authorsIds:
        cursor.execute("""
        INSERT INTO book_authors (book_id, author_id) VALUES (%s, %s)
         ON CONFLICT DO NOTHING;
        """,
        (this_book_id, x)
        )
    
def ExecuteQueryJoinBooksCategories(this_book_id, treatedCategories):
    global cursor
    for x in treatedCategories:
        cursor.execute("""
        INSERT INTO book_categories (book_id, category_name) VALUES (%s, %s)
         ON CONFLICT DO NOTHING;
        """,
        (this_book_id, x)
        )



pd.options.display.max_rows = 30
df = pd.read_csv("dataset.csv")
df["Category"] = df["Category"].fillna("")
df["Description"] = df["Description"].fillna("")
df["Publisher"] = df["Publisher"].fillna("")
print(df)
NofBooks = len(df)

for index, row in df.iterrows():
    # BOOK AUTHORS
    treatedAuthors = TreatAuthors(row["Authors"])
    authorsIds = fromStringToId(treatedAuthors, authors)
    ExecuteQueryAuthors(authorsIds, treatedAuthors)
    
    
    # BOOK CATEGORIES
    treatedCategories = TreatCategories(row["Category"])
    categoriesIds = fromStringToId(treatedCategories, categories)
    ExecuteQueryCategories(treatedCategories)

    # PUBLISHER
    publisher = row["Publisher"].strip()
    if publisher != "":
        ExecuteQueryPublisher(publisher)

    # BOOK TITLE & DESCRIPTION
    bookTitle = row["Title"].strip()
    bookDescription = row["Description"]
    
    # BOOK PRICE
    bookPrice = row["Price"]

    # BOOK MONTH
    bookMonthStr = row["Month"]
    bookMonth = MonthToNumber(bookMonthStr)

    # BOOK YEAR
    bookYear = row["Year"]

    # print(f"Nome Libro: {bookTitle}")
    # print(f"Autori: {treatedAuthors} -> {authorsIds}")
    # print(f"Categorie: {treatedCategories} -> {categoriesIds}")
    # print(f"Publisher: {publisher} -> {getIdOfPublisher(publisher)}")
    # print(f"Descrizione: {bookDescription}")
    # print(f"Data Pubblicazione: {bookMonth}/{bookYear}")
    # print(f"Prezzo: {bookPrice}")

    # BOOK ID
    this_book_id = books_id_counter;

    # ADD BOOK
    ExecuteQueryBook(this_book_id, bookTitle, publisher, bookPrice, bookDescription, bookMonth, bookYear)

    # BOOK JOINS TABLE
    ExecuteQueryJoinBooksAuthors(this_book_id, authorsIds)
    ExecuteQueryJoinBooksCategories(this_book_id, treatedCategories)

    books_id_counter += 1;

    print(f"Book {this_book_id}/{NofBooks} added!")
    # print("#########################")

conn.commit()
cursor.close()
conn.close()

print("Done!")