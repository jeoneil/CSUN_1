package COMP380_MP_Final;

import java.util.LinkedList;

/*
 * Class: Library
 * Purpose: Stores any added audio tracks in a list
 * 	that can later be accessed while the program is
 * 	running.
 * Author: Jack O'Neil
 * Date Created: 5 April, 2016
 */

public class Library {
    LinkedList<Audio> list;

    public Library()
    {
        this.list = new LinkedList<Audio>();
    }

    /*
     * Method: add
     * Purpose: adds audio object to list
     * Input: audio object
     * Output: the added audio object
     * Return value: Audio a;
     * Author: Jack O'Neil
     * Date Created: 5 April, 2016
     */
    public void add(Audio newSong)
    {
        list.add(newSong);
    }

    public void delete(Audio a)
    {
        if (list.isEmpty()) return;
        list.remove(a);
    }

    /*
     * Method: find
     * Purpose: finds song with the specified name
     * Input: song name
     * Output: audio object with that name
     * Return value: Audio a;
     * Author: Jack O'Neil
     * Date Created: 5 April, 2016
     */
    public Audio find(String songName)
    {
        Audio a = null;
        //ensure that list has at least one song
        if (list.isEmpty()) return null;

        //traverse list for an audio file with the requested song name
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).getSongName() == songName)
                a = list.get(i);
        }
        return a;
    }

    /*
     * getters and setters
     */
    public LinkedList<Audio> getList() {
        return list;
    }

    public void setList(LinkedList<Audio> list) {
        this.list = list;
    }


}
