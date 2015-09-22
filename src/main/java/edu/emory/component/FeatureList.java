package main.java.edu.emory.component;

/**
 * @author alexlutz ({@code ajlutz@emory.edu})
 * @version 1.0
 * @since 9/21/15 at 10:47 PM
 */
public interface FeatureList
{
    final int PrevPOSTag = 1,
            PrevPrevPOSTag = 2,
            PrevPrevPrevPOSTag = 3,
            PrevPrevPrevPrevPOSTag = 4,
            NextPOSTag = 5,
            NextNextPOSTag = 6,
            NextNextNextPOSTag = 7,
            NextNextNextNextPOSTag = 8,
            PrevLemma = 9,
            PrevPrevLemma = 10,
            PrevPrevPrevLemma = 11,
            PrevPrevPrevPrevLemma = 12,
            NextLemma = 13,
            NextNextLemma = 14,
            NextNextNextLemma = 15,
            NextNextNextNextLemma = 16,
            CurrentLabel = 17;
}
