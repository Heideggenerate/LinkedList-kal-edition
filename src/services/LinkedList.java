package services;

import errors.ErrorHandler;
import errors.ObjectErrors;
import resultwrapper.ResultWrapper;

public class LinkedList {

    public class Node {
        protected Integer data;
        protected Node nextNode;
        protected Node previousNode;

        protected Node(Integer data, Node nextNode, Node previousNode) {
            this.data = data;
            this.nextNode = nextNode;
            this.previousNode = previousNode;
        }
    }

    LinkedListUtils utils = new LinkedListUtils();

    /**
     * Итератор состояния головы.
     * <p>
     * Сохраняет последний использовавшийся узел основной головы.
     * <br>
     * При обновлении головы обновляет своё состояние и копирует узёл основной головы под заданным номером.
     * <p>
     * Обновление итератора осуществляется через метод {@link #getNodesOnIdxRealisation(int idx, Node head, int size, Node headIterator) getNodesOnIdx}.
     */
    protected Node headIterator;
    protected Node mainHead;
    protected int iteratorSize = 0;
    protected int mainSize = 0;


    //Получение размеров

    public int getListSize() {
        return mainSize;
    }

    public int getListSize(Node head) {
        return utils.calculateListSize(head);
    }


    //Получение значения из узла

    public ResultWrapper<Integer> getValue(Node head) {
        if (head == null) return ErrorHandler.failed(ObjectErrors.EMPTY_OBJECT);
        if (head.data == null) return ErrorHandler.failed(ObjectErrors.EMPTY_OBJECT);
        return ErrorHandler.success(head.data);
    }

    public ResultWrapper<Node> getNextNode(Node head) {
        if (head == null) return ErrorHandler.failed(ObjectErrors.EMPTY_OBJECT);
        return ErrorHandler.success(head.nextNode);
    }

    public ResultWrapper<Node> getPreviousNode(Node head) {
        if (head == null) return ErrorHandler.failed(ObjectErrors.EMPTY_OBJECT);
        return ErrorHandler.success(head.previousNode);
    }

    public ResultWrapper<Node> getNodesOnIdx(int idx, Node head, int size, Node headIterator) {
        return getNodesOnIdxRealisation(idx, head, size, headIterator);
    }

    public ResultWrapper<Node> getNodesOnIdx(int idx) {
        return getNodesOnIdxRealisation(idx, mainHead, mainSize, headIterator);
    }


    //Инициализаторы

    public ResultWrapper<Node> initialization(Node head) {
        return initializationRealisation(head);
    }

    public ResultWrapper<Node> initialization() {
        ResultWrapper<Node> initializationResult = initializationRealisation(mainHead);
        if (initializationResult.getError() == ObjectErrors.NO_ERROR)
            mainHead = initializationResult.getData();
        return ErrorHandler.failed(initializationResult.getError());
    }

    private ResultWrapper<Node> initializationRealisation(Node head) {
        if (head == null)
            head = new Node(null, null, null);
        return ErrorHandler.success(head);
    }

    //Добавление элемента

    public ResultWrapper<Node> addElement(int data, Node head) {
        return addElementRealisation(data, head);
    }

    public ResultWrapper<Node> addElement(int data) {
        ResultWrapper<Node> addElementRealisationResult = addElementRealisation(data, mainHead);
        if (addElementRealisationResult.getError() == ObjectErrors.NO_ERROR)
            mainHead = addElementRealisationResult.getData();
        return ErrorHandler.failed(addElementRealisationResult.getError());
    }

    private ResultWrapper<Node> addElementRealisation(int data, Node head) {
        if (head == null) return new ResultWrapper<>(null,ObjectErrors.EMPTY_OBJECT);
        if (head == mainHead) mainSize++;
        if (head.nextNode == null && head.previousNode == null && head.data == null) {
            head.data = data;
            return ErrorHandler.success(head);
        }
        head = new Node(data, head, null);
        head.nextNode.previousNode = head;
        return ErrorHandler.success(head);
    }






}

