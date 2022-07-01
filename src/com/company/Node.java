package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Node<E> {

    private ArrayList<Entry<E>> entries;
    private ArrayList<Node<E>> children;
    private Node<E> parent;

    public ArrayList<Entry<E>> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry<E>> entries) {
        this.entries = entries;
    }

    public ArrayList<Node<E>> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node<E>> children) {
        this.children = children;
    }

    public Node<E> getParent() {
        return parent;
    }

    public void setParent(Node<E> parent) {
        this.parent = parent;
    }

    public Node(E value , int key) {

        entries=new ArrayList<>();
        entries.add(new Entry(value,key));

        parent=null;

        children=new ArrayList<>();
        children.add(new Node<>());
        children.add(new Node<>());
        children.get(0).setParent(this);
        children.get(1).setParent(this);
    }

    public Node()
    {
        parent=null;
        entries=new ArrayList<>();
        children=new ArrayList<>();
    }

    public Entry<E> addToEntries(int key , E value)
    {
        Entry<E> entry=new Entry<>(value , key);
        entries.add(entry);

        //sorting
        bubbleSort(entries);

        children.add(new Node<>());
        children.get(0).setParent(this);
        children.get(1).setParent(this);

        return entry;
    }

    public void bubbleSort(ArrayList<Entry<E>> entries)
    {
        int n = entries.size();
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (entries.get(j).getKey() > entries.get(j+1).getKey())
                {
                    Entry<E> temp = entries.get(j);
                    entries.set(j,entries.get(j+1));
                    entries.set(j+1,temp);
                }
    }

}
