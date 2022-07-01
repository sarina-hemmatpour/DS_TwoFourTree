package com.company;

import java.util.ArrayList;

public class TwoFourTree<E> {

    private Node<E> root;

    public Node<E> getRoot() {
        return root;
    }

    public void setRoot(Node<E> root) {
        this.root = root;
    }

    public TwoFourTree(E value , int key) {
        root=new Node<>(value , key);
    }
    public TwoFourTree()
    {
        root=new Node<>();
    }



    //methodes
    public Entry<E> search(Node<E> newRoot ,int key)
    {
        int d=newRoot.getChildren().size();

        //tree is null
        if (newRoot==null)
        {
            return null;
        }


        for (int i=0 ; i<newRoot.getEntries().size() ; i++)
        {
            if (newRoot.getEntries().get(i).getKey()==key)
            {
                return newRoot.getEntries().get(i);
            }
        }

        if (d!=0)
        {
            if (key<newRoot.getEntries().get(0).getKey())
            {
                return search(newRoot.getChildren().get(0) , key);
            }

            if (key>newRoot.getEntries().get(d-2).getKey())
            {
                return search(newRoot.getChildren().get(d-1),key);
            }

            for (int i=1 ; i<newRoot.getEntries().size() ; i++)
            {
                if (key>newRoot.getEntries().get(i-1).getKey() && key< newRoot.getEntries().get(i).getKey())
                {
                    return search(newRoot.getChildren().get(i),key);
                }
            }
        }


        return null;

    }


    public Entry<E> insert(E value , int key)
    {
        Entry<E> newEntry=new Entry<>(value , key);

        Entry<E> foudEntry=search(root , key);
        //it already exists
        if (foudEntry!=null)
        {
            foudEntry.setValue(value);
            return foudEntry;
        }

        //it doesnt exist
        Node<E> searchedNode=searchNode(root , key);

        if (searchedNode.getEntries().size()==3)
        {
            System.out.println("NoooooooooooooWay");
        }

            return searchedNode.addToEntries(key,value);



    }

    public Node<E> split(Node<E> node)
    {
        //if its root
        if (this.root==node)
        {
            Node<E> firstPart=new Node<>(node.getEntries().get(0).getValue(),node.getEntries().get(0).getKey());
            firstPart.getChildren().set(0,node.getChildren().get(0));
            firstPart.getChildren().set(1,node.getChildren().get(1));

            Node<E> secPart=new Node<>(node.getEntries().get(2).getValue(),node.getEntries().get(2).getKey());
            secPart.getChildren().set(0,node.getChildren().get(2));
            secPart.getChildren().set(1,node.getChildren().get(3));


            Node<E> newRoot=new Node<>(node.getEntries().get(1).getValue(),node.getEntries().get(1).getKey());
            newRoot.getChildren().set(0,firstPart);
            newRoot.getChildren().set(1,secPart);


            firstPart.setParent(newRoot);
            secPart.setParent(newRoot);

            setRoot(newRoot);

            return newRoot;
        }

        else
        {
            //its not a root
            Node<E> firstPart=new Node<>(node.getEntries().get(0).getValue(),node.getEntries().get(0).getKey());
            firstPart.getChildren().set(0,node.getChildren().get(0));
            firstPart.getChildren().set(1,node.getChildren().get(1));
            firstPart.setParent(node.getParent());

            Node<E> secPart=new Node<>(node.getEntries().get(2).getValue(),node.getEntries().get(2).getKey());
            secPart.getChildren().set(0,node.getChildren().get(2));
            secPart.getChildren().set(1,node.getChildren().get(3));
            secPart.setParent(node.getParent());


            node.getParent().addToEntries(node.getEntries().get(1).getKey(),node.getEntries().get(1).getValue());
            node.getParent().getChildren().remove(node.getParent().getChildren().size()-1);
            node.getParent().getChildren().remove(node.getParent().getChildren().size()-1);
            node.getParent().getChildren().add( firstPart);

            node.getParent().getChildren().add(secPart);

            return node.getParent();

        }


    }

    public Node<E> searchNode(Node<E> r , int key)
    {

        if (r.getEntries().size()==3)
        {
            r=split(r);
        }

        if (r==null)
        {
            return null;
        }
        int d=r.getChildren().size();

        if (d!=0)
        {
            if (r.getChildren().get(0).getChildren().size()==0)
            {
                return r;
            }

            if (key<r.getEntries().get(0).getKey())
            {
                return searchNode(r.getChildren().get(0) , key);
            }

            if (key>r.getEntries().get(d-2).getKey())
            {
                return searchNode(r.getChildren().get(d-1),key);
            }

            for (int i=1 ; i<r.getEntries().size() ; i++)
            {
                if (key>r.getEntries().get(i-1).getKey() && key< r.getEntries().get(i).getKey())
                {
                    return searchNode(r.getChildren().get(i),key);
                }
            }

        }


        return null;

    }

    public Entry<E> delete(int key)
    {
        Entry<E> entryToRemove=search(root,key);

        if (entryToRemove==null)
        {
            return null;
        }

        //it exists

        Node<E> searchedNode=searchNodeForRemoving(getRoot(),key);

        for (int i=0 ; i<searchedNode.getEntries().size() ; ++i)
        {
            if (searchedNode.getEntries().get(i).getKey()==key)
            {
                searchedNode.getEntries().remove(i);
                searchedNode.getChildren().remove(0);
                break;
            }
        }

        return entryToRemove;

    }

    public Node<E> searchNodeForRemoving(Node<E> r , int key)
    {
        if (r.getEntries().size()==1 && r!=root)
        {
            //merge
            //if sibling==1 && parent>1
            if (r.getParent().getEntries().size()>1)
            {
                if ((leftSibling(r)!=null && leftSibling(r).getEntries().size()==1)
                    ||(rightSibling(r)!=null && rightSibling(r).getEntries().size()==1))
                {
                    r=fusion(r);
                }
            }

            //rotate
            //if sibling>1

            if (r.getEntries().size()==1)
            {
                if((leftSibling(r)!=null && leftSibling(r).getEntries().size()>1)
                        ||(rightSibling(r)!=null && rightSibling(r).getEntries().size()>1))
                {
                    r=rotate(r);
                }
            }


            //shrink
            //parent==1 && siblin==1

            if (r.getEntries().size()==1)
            {
                if (r.getParent().getEntries().size()==1)
                {
                    if ((leftSibling(r)!=null && leftSibling(r).getEntries().size()==1)
                            ||(rightSibling(r)!=null && rightSibling(r).getEntries().size()==1))
                    {
                        r=shrink(r);
                    }
                }
            }

        }

        if (r==null)
        {
            return null;
        }
        int d=r.getChildren().size();

        if (d!=0)
        {
            for (int i=0 ; i<r.getEntries().size() ; i++)
            {
                if (r.getEntries().get(i).getKey()==key)
                {
                    //if is leaf ==> delete (except root)
                    if (r.getChildren().get(0).getChildren().size()==0)
                    {
                        return r;
                    }
                    //isnt leaf so we should find the predecessor
                    else
                    {
                        //swap with predecessor
                        Node<E> predecessorN=findPredecessor(r.getChildren().get(i));


                        // ////////////////////////////////////////////////////////////////////////////////////////////////
                        if (predecessorN.getEntries().size()==1 && predecessorN!=root)
                        {
                            //merge
                            //if sibling==1 && parent>1
                            if (predecessorN.getParent().getEntries().size()>1)
                            {
                                if ((leftSibling(predecessorN)!=null && leftSibling(predecessorN).getEntries().size()==1)
                                        ||(rightSibling(predecessorN)!=null && rightSibling(predecessorN).getEntries().size()==1))
                                {
                                    predecessorN=fusion(predecessorN);
                                }
                            }

                            //rotate
                            //if sibling>1
                            if (predecessorN.getEntries().size()==1)
                            {
                                if((leftSibling(predecessorN)!=null && leftSibling(predecessorN).getEntries().size()>1)
                                        ||(rightSibling(predecessorN)!=null && rightSibling(predecessorN).getEntries().size()>1))
                                {
                                    predecessorN=rotate(predecessorN);
                                }
                            }


                            //shrink
                            //parent==1 && siblin==1
                            if (predecessorN.getEntries().size()==1)
                            {
                                if (predecessorN.getParent().getEntries().size()==1)
                                {
                                    if ((leftSibling(predecessorN)!=null && leftSibling(predecessorN).getEntries().size()==1)
                                            ||(rightSibling(predecessorN)!=null && rightSibling(predecessorN).getEntries().size()==1))
                                    {
                                        predecessorN=shrink(predecessorN);
                                    }
                                }
                            }

                        }

                        // ///////////////////////////////////////////////////////////////////////////////////////////////

                        //swap if r still contains the key
                        if (!hasKey(key,predecessorN))
                        {
                            Entry<E> predecessorE=findPredecessor(r.getChildren().get(i)).getEntries().get(
                                    findPredecessor(r.getChildren().get(i)).getEntries().size()-1);

                            predecessorN.getEntries().set(predecessorN.getEntries().size()-1,r.getEntries().get(i));

                            r.getEntries().set(i,predecessorE);
                        }
                        r=predecessorN;
                        return r;

                    }

                }
            }

            if (key<r.getEntries().get(0).getKey())
            {
                return searchNodeForRemoving(r.getChildren().get(0) , key);
            }

            if (key>r.getEntries().get(d-2).getKey())
            {
                return searchNodeForRemoving(r.getChildren().get(d-1),key);
            }

            for (int i=1 ; i<r.getEntries().size() ; i++)
            {
                if (key>r.getEntries().get(i-1).getKey() && key< r.getEntries().get(i).getKey())
                {
                    return searchNodeForRemoving(r.getChildren().get(i),key);
                }
            }

        }


        return null;

    }

    public Node<E> shrink(Node<E> node)
    {
        boolean f=false;
        Node<E> sibling = null;
        if (leftSibling(node)==null)
        {
            sibling=rightSibling(node);
            f=true;
        }
        else if (rightSibling(node)==null)
        {
            sibling=leftSibling(node);
        }
        else if (rightSibling(node).getEntries().size()==1)
        {
            sibling=rightSibling(node);
            f=true;
        }
        else if (leftSibling(node).getEntries().size()==1)
        {
            sibling=leftSibling(node);
        }

        Node<E> newNode=new Node<>(node.getEntries().get(0).getValue(),node.getEntries().get(0).getKey());
        newNode.addToEntries(sibling.getEntries().get(0).getKey(),sibling.getEntries().get(0).getValue());
        newNode.addToEntries(root.getEntries().get(0).getKey(),root.getEntries().get(0).getValue());

        if (!f)
        {
            newNode.getChildren().set(0,sibling.getChildren().get(0));
            newNode.getChildren().set(1,sibling.getChildren().get(1));
            newNode.getChildren().set(2,node.getChildren().get(0));
            newNode.getChildren().set(3,node.getChildren().get(1));
        }
        else
        {
            newNode.getChildren().set(2,sibling.getChildren().get(0));
            newNode.getChildren().set(3,sibling.getChildren().get(1));
            newNode.getChildren().set(0,node.getChildren().get(0));
            newNode.getChildren().set(1,node.getChildren().get(1));
        }
        for (int i=0 ; i<newNode.getChildren().size() ; i++)
        {
            newNode.getChildren().get(i).setParent(newNode);
        }

        if (node.getParent()==root)
        {

            setRoot(newNode);
        }
        else {

            newNode.setParent(node.getParent());

        }

        return newNode;
    }

    public Node<E> rotate(Node<E> node)
    {
        boolean f=false;
        int index=node.getParent().getChildren().indexOf(node);
        Node<E> sibling = null;
        if (leftSibling(node)==null)
        {
            sibling=rightSibling(node);
            f=true;
        }
        else if (rightSibling(node)==null)
        {
            sibling=leftSibling(node);
            index--;
        }
        else if (rightSibling(node).getEntries().size()>1)
        {
            sibling=rightSibling(node);
            f=true;
        }
        else if (leftSibling(node).getEntries().size()>1)
        {
            sibling=leftSibling(node);
            index--;
        }

        if (!f)//left sibiling
        {
            node.addToEntries(node.getParent().getEntries().get(index).getKey(),node.getParent().getEntries().get(index).getValue());
            node.getChildren().set(2,node.getChildren().get(1));
            node.getChildren().set(1,node.getChildren().get(0));
            node.getChildren().set(0,sibling.getChildren().get(sibling.getChildren().size()-1));
            node.getChildren().get(0).setParent(node);

            node.getParent().getEntries().set(index,sibling.getEntries().get(sibling.getEntries().size()-1));

            sibling.getEntries().remove(sibling.getEntries().size()-1);
            sibling.getChildren().remove(sibling.getChildren().size()-1);
        }
        else //right sibling
        {
            node.addToEntries(node.getParent().getEntries().get(index).getKey() ,
                    node.getParent().getEntries().get(index).getValue());
            node.getChildren().set(2,sibling.getChildren().get(0));
            node.getChildren().get(2).setParent(node);

            node.getParent().getEntries().set(index,sibling.getEntries().get(0));

            sibling.getEntries().remove(0);
            sibling.getChildren().remove(0);
        }


        return node;

    }

    public Node<E> fusion(Node<E> node)
    {
        int index=node.getParent().getChildren().indexOf(node);
        Node<E> sibling = null;
        if (leftSibling(node)==null)
        {
            sibling=rightSibling(node);
        }
        else if (rightSibling(node)==null)
        {
            sibling=leftSibling(node);
            index--;
        }
        else if (rightSibling(node).getEntries().size()==1)
        {
            sibling=rightSibling(node);
        }
        else if (leftSibling(node).getEntries().size()==1)
        {
            sibling=leftSibling(node);
            index--;
        }

        Node<E> newNode=new Node<>(node.getEntries().get(0).getValue(),node.getEntries().get(0).getKey());
        newNode.addToEntries(sibling.getEntries().get(0).getKey(),sibling.getEntries().get(0).getValue());
        newNode.addToEntries(node.getParent().getEntries().get(index).getKey() ,
                node.getParent().getEntries().get(index).getValue());
        newNode.setParent(node.getParent());
        newNode.getChildren().set(0,node.getChildren().get(0));
        newNode.getChildren().set(1,node.getChildren().get(1));
        newNode.getChildren().set(2,sibling.getChildren().get(0));
        newNode.getChildren().set(3,sibling.getChildren().get(1));
        newNode.getChildren().get(2).setParent(newNode);
        newNode.getChildren().get(3).setParent(newNode);


        node.getParent().getEntries().remove(index);
        node.getParent().getChildren().set(index,newNode);
        node.getParent().getChildren().remove(index+1);


        return newNode;

    }

    public Node<E> leftSibling(Node<E> node)
    {
        int index=node.getParent().getChildren().indexOf(node);
        if (index==0)
        {
            return null;
        }
        else
            return node.getParent().getChildren().get(index-1);
    }
    public Node<E> rightSibling(Node<E> node)
    {
        int index=node.getParent().getChildren().indexOf(node);
        if (index==node.getParent().getChildren().size()-1)
        {
            return null;
        }
        else
            return node.getParent().getChildren().get(index+1);
    }



    public Node<E> findPredecessor(Node<E> root)
    {
        if (root.getChildren().get(0).getChildren().size()==0)
        {
            return root;
        }

        return findPredecessor(root.getChildren().get(root.getChildren().size()-1));
    }


    public boolean hasKey(int key , Node<E> node)
    {
        for (int i=0 ; i<node.getEntries().size() ; i++)
        {
            if (node.getEntries().get(i).getKey()==key)
                return true;
        }
        return false;
    }



    public void traverse(Node<E> root)
    {
        for (int i=0 ; i<root.getChildren().size();i++)
        {
            traverse(root.getChildren().get(i));
        }

        boolean f=false;

        for (int i=0 ; i<root.getEntries().size() ; i++)
        {
            System.out.print(root.getEntries().get(i).getKey()+" , ");
            f=true;
        }
        if (f)
        {
            System.out.println();
        }

    }

    public ArrayList<Node<E>> siblings(Node<E> node)
    {
        if (node==root)
        {
            ArrayList<Node<E>> arrayList=new ArrayList<>();
            arrayList.add(node);
            return arrayList;
        }
        return node.getParent().getChildren();
    }

    public void printTree()
    {
        Node<E> r=root;

        while (r.getEntries().size()!=0)
        {
            for (int i=0 ; i<siblings(r).size() ; i++)
            {
                System.out.print("{");
                for (int j=0 ; j<siblings(r).get(i).getEntries().size() ; j++)
                {
                    System.out.print("("+siblings(r).get(i).getEntries().get(j).getValue()+","+siblings(r).get(i).getEntries().get(j).getKey()+")");
                    if (j!=siblings(r).get(i).getEntries().size()-1)
                    {
                        System.out.print(" , ");
                    }
                    else
                    {
                        System.out.print("}");
                    }
                }
                System.out.print("\t");
            }

            System.out.println();

            r=r.getChildren().get(0);
        }

    }

}
