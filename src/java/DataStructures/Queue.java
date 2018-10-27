package DataStructures;

public class Queue<Type> extends List {

    public Queue() {
    }

    public void printInCol(Type[] usersCahrs) {
        for (int i = 0; i < usersCahrs.length; i++) {
            super.InsertAfter(usersCahrs[i] + "\n");
        }

        System.out.println(super.toString());
    }

    public void enqueue(Type data) {
        super.Last();
        super.InsertAfter(data);
    }

    public Object dequeue() {
        super.First();
        Object temp = super.GetValue();
        super.Remove();
        return temp;
    }


}
