import services.LinkedList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.initialization();
        linkedList.addElement(50);
        linkedList.addElement(50);
        linkedList.addElement(30);
        linkedList.addElement(30);
        linkedList.addElement(40);
        linkedList.addElement(30);

        LinkedList.Node opa = linkedList.initialization(null).getData();
        opa = linkedList.addElement(30, opa).getData();
        opa = linkedList.addElement(40, opa).getData();
        opa = linkedList.addElement(50, opa).getData();
        opa = linkedList.addElement(8, opa).getData();

        LinkedList.Node result = linkedList.nodeSubstraction(opa).getData();
        int size = linkedList.getListSize(result);

        for (int i = 0; i < size; i++) {
            System.out.println(linkedList.getValue(linkedList.getNodes(i, result, size).getData()).getData());
        }
    }
}