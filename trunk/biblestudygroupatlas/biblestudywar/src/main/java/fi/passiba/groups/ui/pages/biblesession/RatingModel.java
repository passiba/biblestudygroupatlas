/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.passiba.groups.ui.pages.biblesession;

import org.apache.wicket.IClusterable;

/**
 * Rating model for storing the ratings, typically this comes from a database.
 */
public class RatingModel implements IClusterable {

    private int nrOfVotes = 0;
    private int sumOfRatings = 0;
    private double rating = 0;
    private long chapterid=0;

    /**
     * Returns whether the star should be rendered active.
     *
     * @param star
     *            the number of the star
     * @return true when the star is active
     */
    public boolean isActive(int star) {
        return star < ((int) (rating + 0.5));
    }

    /**
     * Gets the number of cast votes.
     *
     * @return the number of cast votes.
     */
    public Integer getNrOfVotes() {
        return nrOfVotes;
    }

    /**
     * Adds the vote from the user to the total of votes, and calculates the rating.
     *
     * @param nrOfStars
     *            the number of stars the user has cast
     */
    public void addRating(int nrOfStars) {
        nrOfVotes++;
        sumOfRatings += nrOfStars;
        rating = sumOfRatings / (1.0 * nrOfVotes);
    }

    /**
     * Gets the rating.
     *
     * @return the rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * Returns the sum of the ratings.
     *
     * @return the sum of the ratings.
     */
    public int getSumOfRatings() {
        return sumOfRatings;
    }

    public void setNrOfVotes(int nrOfVotes) {
        this.nrOfVotes = nrOfVotes;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setSumOfRatings(int sumOfRatings) {
        this.sumOfRatings = sumOfRatings;
    }

    public long getChapterid() {
        return chapterid;
    }

    public void setChapterid(long chapterid) {
        this.chapterid = chapterid;
    }
    
}
