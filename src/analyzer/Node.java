package analyzer;

class SymbolTable {
    public static final int MAX_SYMBOLS = 100; // Assuming MAX_SYMBOLS is a constant value
    Symbol[] symbols;

    public SymbolTable() {
        this.symbols = new Symbol[MAX_SYMBOLS];
        for (int i = 0; i < MAX_SYMBOLS; i++) {
            this.symbols[i] = new Symbol();
        }
    }
}

public class Node {
    SymbolTable symbolTable;
    int count;
    Node next;

    public Node() {
        this.symbolTable = new SymbolTable();
        this.count = 0;
        this.next = null;
    }

    public Node pushScope() {
        Node newNode = new Node();

        Node current = this;
        while (current.next!=null){
            current = current.next;
        }

        current.next = newNode;
        return newNode;
    }

    public Node popScope() {
        if (this.next != null) {
            Node current = this;
            Node last = this;
            while (current.next!=null){
                last = current;
                current = current.next;
            }
            last.next = null;
            return current;
        } else {
            System.out.println("Cannot pop from an empty symbol table");
            return this;
        }
    }

    public void printCurrentScope() {
        if (this.next != null) {

            Node current = this;
            while (current.next!=null){
                current = current.next;
            }

            System.out.println("Current Scope Symbols:");
            for (int i = 0; i < current.count; i++) {
                System.out.println(current.symbolTable.symbols[i].name + "\t " + current.symbolTable.symbols[i].value);
            }
            System.out.println("\n");
        } else {
            System.out.println("Symbol table is empty");
        }
    }

    public void printAllScopes() {
        Node current = this.next;
        while (current != null) {
            System.out.println("Scope Symbols:");
            for (int i = 0; i < current.count; i++) {
                System.out.println(current.symbolTable.symbols[i].name + "\t " + current.symbolTable.symbols[i].value);
            }
            System.out.println("\n");
            current = current.next;
        }
    }

    public void insertSymbol(String symbolType, String symbolName, String symbolValue) {

        if (this.next==null){
            System.out.println("Cannot add symbol because currently no scope exists.");
        }
        Node current = this;
        while (current.next!=null){
            current = current.next;
        }
        if (current.count < SymbolTable.MAX_SYMBOLS) {

            Symbol symbol = new Symbol();

            symbol.name = symbolName;
            symbol.value = symbolValue;
            symbol.type = symbolType;

            current.symbolTable.symbols[current.count++] = symbol;

        } else {
            System.out.println("Symbol table is full");
        }
    }

    public Symbol symbolExists(String name) {
        Node current = this.next;
        while (current != null) {
            for (int i = 0; i < current.count; i++) {
                if (current.symbolTable.symbols[i].name.equals(name)) {
                    return current.symbolTable.symbols[i];
                }
            }
            current = current.next;
        }
        return null;
    }

    public Symbol symbolExistsInCurrent(String name) {
        if (this.next==null){
            return null;
        }
        Node current = this;
        while (current.next!=null){
            current = current.next;
        }

        for (int i = 0; i < current.count; i++) {
            if (current.symbolTable.symbols[i].name.equals(name)) {
                return current.symbolTable.symbols[i];
            }
        }
        return null;
    }


    public void freeEnvironment() {
//        while (this != null) {
//            Node temp = this;
//            this.next = temp.next;
//            temp = null;
//        }
        this.next = null;
    }

     public static void main(String[] args) {

         Node stack = new Node(); // Create an initial empty stack

            // Push three scopes
         stack.pushScope();
         stack.insertSymbol("int", "x", "10");
         stack.insertSymbol("int", "d", "1000");
         stack.printAllScopes();
         stack.pushScope();
         stack.insertSymbol("int", "y", "15");
         stack.printAllScopes();
         stack.pushScope();
         stack.insertSymbol("int", "z", "20");
         stack.printAllScopes();


            // Pop three scopes
         stack.popScope();
         stack.printCurrentScope();
         stack.popScope();
         stack.printCurrentScope();
         stack.popScope();
         stack.printCurrentScope();

         //     // Example usage:
    //     Node head = new Node();
    //     head.pushScope();
    //     head.insertSymbol("int", "x", "10");
    //     head.printCurrentScope();

    //     head.popScope();
    //     head.printCurrentScope();

    //     head.freeEnvironment();
     }
}