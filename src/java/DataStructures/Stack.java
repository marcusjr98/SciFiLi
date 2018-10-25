package DataStructures;

public class Stack<Type> extends List {

    public Stack(){
    }

    public void push(Type data){
        super.InsertBefore(data);
    }

    public Object pop(){
        super.First();
        Object temp = super.GetValue();
        super.Remove();
        return temp;
    }

    public Object peek(){
        super.First();
        return super.GetValue();
    }
}