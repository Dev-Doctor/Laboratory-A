/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/**
 * Represents a star shape that can be used to display ratings or other visual elements.
 * <p>
 * This class extends {@link SVGPath} to create a star shape using SVG path data. It includes methods
 * to set ratings, resize stars, and generate a series of stars on a given {@link Pane}.
 * </p>
 * @author DevDoctor
 * @since 1.0
 * @see SVGPath
 */
public class Star extends SVGPath {
    /**
     * Creates a new {@code Star} instance with the default star SVG path content.
     * <p>
     * The star shape is defined by the SVG path data set in this constructor. The star is initially
     * set with a transparent fill and a black stroke.
     * </p>
     */
    public Star() {
        /*this.setContent("M26.285,2.486l5.407,10.956c0.376,0.762,1.103,1.29,1.944,"
                + "1.412l12.091,1.757  c2.118,0.308,2.963,2.91,1.431,4.403l-8.749,8.528c"
                + "-0.608,0.593-0.886,1.448-0.742,2.285l2.065,12.042  c0.362,2.109-1.852,"
                + "3.717-3.746,2.722l-10.814-5.685c-0.752-0.395-1.651-0.395-2.403,0l-10.814,5.685"
                + "  c-1.894,0.996-4.108-0.613-3.746-2.722l2.065-12.042c0.144-0.837-0.134-1.692"
                + "-0.742-2.285l-8.749-8.528  c-1.532-1.494-0.687-4.096,1.431-4.403l12.091-1.757c0.841"
                + "-0.122,1.568-0.65,1.944-1.412l5.407-10.956  C22.602,0.567,25.338,0.567,26.285,2.486z");*/
        this.setContent("M11.2691 4.41115C11.5006 3.89177 11.6164 3.63208 11.7776 3.55211C11.9176 3.48263 12.082 3.48263 12.222 " +
                "3.55211C12.3832 3.63208 12.499 3.89177 12.7305 4.41115L14.5745 8.54808C14.643 8.70162 14.6772 8.77839 14.7302" +
                " 8.83718C14.777 8.8892 14.8343 8.93081 14.8982 8.95929C14.9705 8.99149 15.0541 9.00031 15.2213 9.01795L19.7256 " +
                "9.49336C20.2911 9.55304 20.5738 9.58288 20.6997 9.71147C20.809 9.82316 20.8598 9.97956 20.837 10.1342C20.8108 " +
                "10.3122 20.5996 10.5025 20.1772 10.8832L16.8125 13.9154C16.6877 14.0279 16.6252 14.0842 16.5857 14.1527C16.5507 " +
                "14.2134 16.5288 14.2807 16.5215 14.3503C16.5132 14.429 16.5306 14.5112 16.5655 14.6757L17.5053 19.1064C17.6233 " +
                "19.6627 17.6823 19.9408 17.5989 20.1002C17.5264 20.2388 17.3934 20.3354 17.2393 20.3615C17.0619 20.3915 16.8156 " +
                "20.2495 16.323 19.9654L12.3995 17.7024C12.2539 17.6184 12.1811 17.5765 12.1037 17.56C12.0352 17.5455 11.9644 " +
                "17.5455 11.8959 17.56C11.8185 17.5765 11.7457 17.6184 11.6001 17.7024L7.67662 19.9654C7.18404 20.2495 " +
                "6.93775 20.3915 6.76034 20.3615C6.60623 20.3354 6.47319 20.2388 6.40075 20.1002C6.31736 19.9408 6.37635 1" +
                "9.6627 6.49434 19.1064L7.4341 14.6757C7.46898 14.5112 7.48642 14.429 7.47814 14.3503C7.47081 14.2807 " +
                "7.44894 14.2134 7.41394 14.1527C7.37439 14.0842 7.31195 14.0279 7.18708 13.9154L3.82246 10.8832C3.40005 " +
                "10.5025 3.18884 10.3122 3.16258 10.1342C3.13978 9.97956 3.19059 9.82316 3.29993 9.71147C3.42581 9.58288 " +
                "3.70856 9.55304 4.27406 9.49336L8.77835 9.01795C8.94553 9.00031 9.02911 8.99149 9.10139 8.95929C9.16534 " +
                "8.93081 9.2226 8.8892 9.26946 8.83718C9.32241 8.77839 9.35663 8.70162 9.42508 8.54808L11.2691 4.41115Z");
        setFill(Color.TRANSPARENT);
        setStroke(Color.BLACK);
//        setScaleX(2);
//        setScaleY(2);
    }

    /**
     * Clears the fill color and style class of the star, making it transparent.
     * <p>
     * This method is used to reset the appearance of the star, typically before applying a new rating.
     * </p>
     */
    public void clearStar() {
        setFill(Color.TRANSPARENT);
        getStyleClass().clear();
    }

    /**
     * Sets the rating for an array of {@code Star} objects.
     * <p>
     * The rating is a floating-point value where the integer part represents fully filled stars,
     * and the fractional part represents a partially filled star. The star color is set based on
     * the rating value.
     * </p>
     *
     * @param value the rating value (between 0 and 5)
     * @param stars an array of {@code Star} objects to set the rating for
     */
    public static void setRating(Float value, Star[] stars) {
        for (Star star : stars)
            star.clearStar();

        int intPart = value.intValue();
        float floatPart = value - intPart;

        for (int i = 0; i < intPart; i++)
            stars[i].setFill(Color.BLUEVIOLET);

        if (floatPart >= 0.5) {
            stars[intPart].setStyle("-fx-fill:linear-gradient(to right, blueviolet "
                    + floatPart * 100 + "%, transparent "
                    + 100 * (1 - floatPart) + "%) ;");
        } else {
            stars[intPart].setStyle("-fx-fill:linear-gradient(to left, transparent "
                    + 100 * (1 - floatPart)+ "%, blueviolet "
                    +  floatPart * 100 + "%) ;");
        }
    }

    /**
     * Resizes the {@code Star} to fit the specified width and height.
     * <p>
     * This method calculates the scaling factors based on the desired dimensions and applies them
     * to the star's scale properties.
     * </p>
     *
     * @param star   the {@code Star} object to resize
     * @param width  the desired width
     * @param height the desired height
     */
    public static void resize(Star star, int width, int height) {
        double originalWidth = star.prefWidth(-1);
        double originalHeight = star.prefHeight(originalWidth);

        double scaleX = width / originalWidth;
        double scaleY = height / originalHeight;

        star.setScaleX(scaleX);
        star.setScaleY(scaleY);
    }


    /**
     * Generates a set of stars based on the given rating and adds them to a {@link Pane}.
     * <p>
     * This method creates an array of stars, sets their rating, and then adds them to the provided
     * {@code Pane}. The maximum rating value is capped at 4.9.
     * </p>
     *
     * @param pane the {@code Pane} to add the stars to
     * @param rating the rating value (between 0 and 5)
     */
    public static void generateStars(Pane pane, Float rating) {
        Star[] stars = new Star[]{new Star(),new Star(),new Star(),new Star(),new Star()};
        if(rating > 4) {
            rating = 4.9f;
        }
        Star.setRating(rating, stars);
        pane.getChildren().setAll(stars);
    }
}