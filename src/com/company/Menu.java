package com.company;

import java.util.Scanner;

public class Menu {

    TwoFourTree<Integer> tree;

    Scanner input=new Scanner(System.in);

    public void mainMenu()
    {


        while (true)
        {
            System.out.println("1.insert\n2.delete\n3.search\n4.show\n5.exit");

            int choice=input.nextInt();


            switch (choice)
            {
                case 1:
                    insertMenu();
                    break;
                case 2:
                    deletMenu();
                    break;
                case 3:
                    searchMenu();
                    break;
                case 4:
                    if (tree==null)
                    {
                        System.out.println("You have no tree!");
                    }
                    else
                    {
                        tree.printTree();
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Wrong input!");
            }
            if (choice==5)
            {
                break;
            }
        }

    }

    public void deletMenu()
    {
        System.out.print("Key: ");
        int key=input.nextInt();
        if (tree.delete(key)==null)
        {
            System.out.println("There isnt such s key in the tree!!!!");
        }
        else
        {
            System.out.println("done!!!!");
        }
    }

    public void searchMenu()
    {
        System.out.print("Key: ");
        int key=input.nextInt();
        if (tree.search(tree.getRoot() , key )==null)
        {
            System.out.println();
            System.out.println("It doesnt exist:(");
        }
        else if (tree.search(tree.getRoot(),key).getKey()==key)
        {
            System.out.println();
            System.out.println("Found it!!! ==> ("+tree.search(tree.getRoot(),key).getValue()+","+
                    tree.search(tree.getRoot(),key).getKey()+")");
        }
    }

    public void insertMenu()
    {
        if (tree==null)
        {
            System.out.println("Your New Tree: ");

            System.out.print("Root Value:");
            int value=input.nextInt();
            System.out.println();

            System.out.print("Root Key:");
            tree=new TwoFourTree<>(value,input.nextInt());
            System.out.println();
            System.out.println("Yay!!!");
        }
        else
        {
            System.out.println("Enter 0 if its enough");
            while (true)
            {
                int choice=input.nextInt();
                if (choice==0)
                {
                    break;
                }

                System.out.print("Value: ");
                int value=input.nextInt();
                System.out.println();

                System.out.print("Key: ");
                tree.insert(value,input.nextInt());
                System.out.println();
                System.out.println("-----------------------");


            }
        }
    }


}
