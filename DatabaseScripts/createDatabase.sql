CREATE TABLE USERS (
    user_id         serial                  NOT NULL,
    first_name      varchar(30)             NOT NULL,
    last_name       varchar(30)             NOT NULL,
    fiscal_code     varchar(16)             NOT NULL,
    email           varchar(255)            NOT NULL,
    password_hash   varchar(256)            NOT NULL,
    is_admin        boolean DEFAULT false NULL,
    CONSTRAINT users_pk PRIMARY KEY (userid),
    CONSTRAINT users_fiscalcode_unique UNIQUE (fiscal_code),
    CONSTRAINT users_email_unique UNIQUE (email)
);

CREATE TABLE EDITORS (
    editor_id   serial PRIMARY KEY,
    edtior_name varchar(200) not null
);

CREATE TABLE AUTHORS (
    author_id   serial PRIMARY KEY,
    author_name varchar(30) not null
);

CREATE TABLE CATEGORIES (
    category_name varchar(150) primary key
);

CREATE TABLE BOOKS (
    book_id         serial PRIMARY KEY,
    title           TEXT not null,
    editor          serial REFERENCES EDITORS (EditorId),
    price           float null
    description     TEXT null,
    publish_date    DATE null
);

CREATE TABLE BOOK_AUTHORS (
    book_id     INTEGER NOT NULL,
    author_id   INTEGER NOT NULL,
    PRIMARY KEY (BookId, AutorId),
    FOREIGN KEY (BookId) REFERENCES BOOKS (BookId),
    FOREIGN KEY (AutorId) REFERENCES AUTORS (AutorId)
);


CREATE TABLE BOOK_CATEGORIES (
    book_id         INTEGER NOT NULL,
    category_name   varchar(150) NOT NULL,
    PRIMARY KEY (BookId, categoryName),
    FOREIGN KEY (BookId) REFERENCES BOOKS (BookId),
    FOREIGN KEY (categoryName) REFERENCES CATEGORIES (categoryName)
);

CREATE TABLE REVIEWS (
    book_id             INTEGER NOT NULL,
    user_id             INTEGER NOT NULL,
    style_vote          smallint not null CHECK (styleVote >= 0 AND styleVote <= 10),
    style_text          varchar(256),
    content_vote        smallint not null CHECK (contentVote >= 0 AND contentVote <= 10),
    content_text        varchar(256),
    niceness_vote       smallint not null CHECK (nicenessVote >= 0 AND nicenessVote <= 10),
    niceness_text       varchar(256),
    originality_vote    smallint not null CHECK (originalityVote >= 0 AND originalityVote <= 10),
    originality_text    varchar(256),
    edition_vote        smallint not null CHECK (editionVote >= 0 AND editionVote <= 10),
    edition_text        varchar(256),

    PRIMARY KEY (BookId, UserId),
    FOREIGN KEY (BookId) REFERENCES BOOKS (BookId),
    FOREIGN KEY (UserId) REFERENCES USERS (UserId)
);

CREATE TABLE LIBRARIES (
    library_id      serial PRIMARY KEY,
    library_name    varchar(40) not null,
    library_owner   INTEGER REFERENCES USERS (UserId)
);

CREATE TABLE LIBRARIES_ENTRIES (
    library_id  INTEGER REFERENCES LIBRARIES (LibraryId),
    book_id     INTEGER REFERENCES BOOKS (BookId),

    PRIMARY KEY (LibraryId, BookId)
);

CREATE TABLE user_tokens (
    token TEXT PRIMARY KEY,
    user_id INTEGER REFERENCES users(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '30 days'
);