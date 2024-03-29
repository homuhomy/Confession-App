package ds.confessionapp.adminPanel;

import java.util.LinkedList;

public class Queue<E> {

    public LinkedList<E> getList() {
        return list;
    }

    private LinkedList<E> list = new LinkedList();

    public void setList(LinkedList<E> list) {
        this.list = list;
    }

    public Queue(){

    }

    public Queue(E[] e){
        for(int i=0; i<e.length;i++)
            list.add(e[i]);
    }

    public void enqueue(E e){
        list.addLast(e); //add element at the end of the queue
    }

    public E dequeue(){
        E e = list.removeFirst(); //removes first element in the queue
        return e;
    }

    public void removeFirs(){
        list.removeFirst();
    }
    public E getElement(int i){
        E element = list.get(i); //get the i-th element but don't remove it
        return element;
    }

    public E peek(){
        return list.element(); //shows first
    }

    public int getSize(){
        return list.size();
    }

    public boolean contain(E e){
        boolean status = false;
        for (E list1 : list) {
            if(e == list1)
                status = true;
        }
        return status;
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public E remove(int i){
        E elements = list.peek();
        for(int j=0; j<i;i++) {
            elements = (E) ("" + list.get(j));
            list.remove(j);
        }
        return elements;
    }

    public void clear(){
        list.clear();
    }
    @Override
    public String toString() {
        return "Queue: " + list.toString();
    }

//    public String print(){
//        Queue temp = new Queue<>();
//        for(int i = 0; i<size;i++){
//            temp.enqueue(this.getElement(i));
//        }
//
//        while(!temp.isEmpty()){
//
//        }
//    }


    public void setElement(int i, E e){
        list.set(i, e);
    }

}