package DataStructures;/* ***************************************************
 * Marcus Castille Jr.
 * 09/26/2018
 *
 * Node Class - handles any form of data
 *************************************************** */

public class List<Type> {
    // We don't actually have to set a max size with linked lists
    // But it is a good idea.
    // Just picture an infinite loop adding to the list! :O
    public static final int MAX_SIZE = 300;

    private Node<Type> head;
    private Node<Type> tail;
    private Node<Type> curr;
    private int num_items;

    // constructor
    // remember that an empty list has a "size" of -1 and its "position" is at -1
    public List() {
        head = this.tail = this.curr = null;
        this.num_items = 0;
    }

    // copy constructor
    // clones the list l and sets the last element as the current
    public List(List<Type> l) {
        Node<Type> n = l.head;
        this.head = this.tail = this.curr = null;
        this.num_items = 0;

        while (n != null) {
            this.InsertAfter(n.getData());
            n = n.getLink();
        }
    }

    // navigates to the beginning of the list
    public void First() {
        try {
            curr = head.getLink();
        } catch (NullPointerException e) {
        }
    }

    // navigates to the end of the list
    // the end of the list is at the last valid item in the list
    public void Last() {
        try {
            curr = tail;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // navigates to the specified element (0-index)
    // this should not be possible for an empty list
    // this should not be possible for invalid positions
    public void SetPos(int pos) {
        if (pos < GetSize()) {
            First();
            for (int x = 0; x < pos; x++) {
                curr = curr.getLink();
            }
        }
    }

    // navigates to the previous element
    // this should not be possible for an empty list
    // there should be no wrap-around
    public void Prev() {
        Node<Type> temp = curr;
        curr = head;
        boolean found = false;
        try {
            while (!found) {
                if (curr.getLink().equals(temp)) {
                    found = true;
                } else {
                    Next();
                }
            }
        } catch (NullPointerException e) {
        }
    }

    // navigates to the next element
    // this should not be possible for an empty list
    // there should be no wrap-around
    public void Next() {
        if (curr.getLink() != null) {
            curr = curr.getLink();
        }
    }

    // returns the location of the current element (or -1)
    public int GetPos() {
        Node<Type> temp = curr;
        First();
        try {
            for (int i = 0; i < num_items; i++) {
                if (curr == temp) {
                    return i;
                } else {
                    curr = curr.getLink();
                }
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    // returns the value of the current element (or -1)
    public Type GetValue() {
        try {
            return curr.getData();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // returns the size of the list
    // size does not imply capacity
    public int GetSize() {
        int count = 0;
        boolean NotEnd = true;
        Node<Type> scroller;
        scroller = head;
        try {
            while (NotEnd) {
                if (scroller.getLink() != null) {
                    count++;
                    scroller = scroller.getLink();
                } else {
                    tail.setLink(scroller);
                    NotEnd = false;
                    num_items = count;
                    return count;
                }
            }
        } catch (NullPointerException e) {
        }
        return 0;
    }

    // inserts an item before the current element
    // the new element becomes the current
    // this should not be possible for a full list
    public void InsertBefore(Type data) {
        if (GetSize() != MAX_SIZE) {
            Prev();
            InsertAfter(data);
        }
    }

    // inserts an item after the current element
    // the new element becomes the current
    // this should not be possible for a full list
    public void InsertAfter(Type data) {
        Node<Type> newNode = new Node<>();
        newNode.setData(data);
        if (newNode.getData() != null) {
            if (GetSize() != MAX_SIZE) {
                if (head == null) {
                    head = new Node<>();
                    tail = new Node<>();
                    curr = new Node<>();
                    tail.setLink(newNode);
                    head.setLink(newNode);
                    curr = head.getLink();
                } else {
                    newNode.setLink(curr.getLink());
                    curr.setLink(newNode);
                    tail.setLink(newNode);
                    curr = curr.getLink();
                }
            }
        }
    }

    // removes the current element
    // this should not be possible for an empty list
    public void Remove() {
        if (head.getLink() != null) {
            Node<Type> temp = curr;
            int position = GetPos();
            curr = head;
            for (int search = 0; search < GetSize(); search++) {
                if (curr.getLink() == temp) {
                    curr.setLink(curr.getLink().getLink());
                    break;
                } else {
                    Next();
                }
            }
            if (curr.getLink() != null) {
                SetPos(position);
            }
        }
    }

    // replaces the value of the current element with the specified value
    // this should not be possible for an empty list
    public void Replace(Type data) {
        if (head.getLink() != null) {
            curr.setData(data);
        }
    }

    // returns if the list is empty
    public boolean IsEmpty() {
        if(head == null)
            return true;

        if (head.getLink() != null) {
            return false;
        }
        return true;
    }

    // returns if the list is full
    public boolean IsFull() {
        int size = GetSize();
        return (size==MAX_SIZE);
    }

    // returns if two lists are equal (by value)
    public boolean Equals(List<Type> l) {
        Node<Type> origin = head.getLink();
        Node<Type> al = l.head.getLink();
        boolean same = true;
        if (GetSize() == l.GetSize()) {
            for (int i = 0; i < GetSize(); i++) {
                if (origin.getData() != al.getData()) {
                    same = false;
                    break;
                }
                origin = origin.getLink();
                al = al.getLink();
            }
        } else {
            same = false;
        }
        return same;
    }

    // returns the concatenation of two lists
    // l should not be modified
    // l should be concatenated to the end of *this
    // the returned list should not exceed MAX_SIZE elements
    // the last element of the new list is the current
    public List<Type> Add(List<Type> l) {
        List<Type> returning = new List<>(this);
        Node<Type> add = null;
        Node<Type> ition = null;
        if (returning.head != null) {
            add = returning.tail.getLink();
        } else {
            returning.head = new Node<>();
            returning.tail = new Node<>();
        }
        if (l.head != null) {
            ition = l.head.getLink();
        }

        if (GetSize() <= MAX_SIZE) {
            if (add == null && ition != null) {
                returning.head.setLink(ition);
            } else if (add != null && ition == null) {
                ;
            } else {
                add.setLink(ition);
            }
        }
        returning.GetSize();
        returning.Last();
        returning.Next();
        return returning;
    }

    // returns a string representation of the entire list (e.g., 1 2 3 4 5)
    // the string "NULL" should be returned for an empty list
    public String toString() {
        String output = "";
        int location = GetPos();
        boolean next;
        try {
            if (head == null || head.getLink() == null) {
                return "NULL";
            }
            First();
            do {
                output += curr.getData() + "";
                next = curr.getLink() != null;
                Next();
            } while (next);
        } catch (NullPointerException e) {
            System.out.println("a null pointer exception has happened.");
        }
        SetPos(location);
        return output;
    }
}