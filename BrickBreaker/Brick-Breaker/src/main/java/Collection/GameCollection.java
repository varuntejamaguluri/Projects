// package main.java.Collection;

// import java.util.ArrayList;
// import main.java.Interfaces.ICollection;
// import main.java.Interfaces.IIterator;
// import main.java.Objects.GameObject;

// //Class for a user defined collection for the Iterator design Pattern
// public class GameCollection implements ICollection {

//     private ArrayList<GameObject> objCollection;

//     public GameCollection() {

//         objCollection = new ArrayList<GameObject>();

//     }

//     // Method to add
//     @Override
//     public boolean add(GameObject obj) {

//         if (obj != null) {

//             objCollection.add(obj);
//             return true;

//         } else
//             return false;

//     }

//     // method to return an iterator over the collection
//     @Override
//     public IIterator getIterator() {

//         return new ArrayIterator();

//     }

//     // method to remove from collection
//     @Override
//     public boolean remove(GameObject obj) {

//         if (obj != null) {

//             objCollection.remove(obj);
//             return true;

//         } else
//             return false;
//     }

//     // Inner class
//     private class ArrayIterator implements IIterator {

//         private int currentIndex;

//         public ArrayIterator() {

//             currentIndex = -1;

//         }

//         @Override
//         public boolean hasNext() {

//             if (objCollection.size() <= 0)
//                 return false;
//             if (currentIndex == objCollection.size() - 1)
//                 return false;
//             else
//                 return true;

//         }

//         @Override
//         public GameObject getNext() {

//             currentIndex++;
//             return objCollection.get(currentIndex);

//         }

//     }

//     // returns size of the collection
//     @Override
//     public int size() {

//         return objCollection.size();
//     }

// }