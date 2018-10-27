package DataStructures;

import library.Book;

public class PriorityQueue<Type> extends Queue {

    public void insert(Type data, int priority) {
        if (!super.IsEmpty()) {
            super.First();
            Book book = (Book) super.GetValue();
            int importance = book.getImportance();
            while (importance < priority) {
                if (super.GetPos() == super.GetSize() - 1) {
                    super.InsertAfter(data);
                    return;
                }
                super.Next();
                book = (Book) super.GetValue();
                importance = book.getImportance();

            }
        }
        super.InsertBefore(data);

    }

    public int getHighestPriority() {
        super.First();
        return super.GetPos();
    }

    public Object removeMax() {
        super.First();
        Object temp = super.GetValue();
        super.Remove();
        return temp;
    }

}
