package services;

import errors.ErrorHandler;
import errors.ObjectErrors;
import resultwrapper.ResultWrapper;

public class LinkedList {

    private class Node {
        private Integer data;
        private Node nextNode;
        private Node previousNode;

        Node(Integer data, Node nextNode, Node previousNode) {
            this.data = data;
            this.nextNode = nextNode;
            this.previousNode = previousNode;
        }
    }

    /**
     * Итератор состояния головы.
     * <p>
     * Сохраняет последний использовавшийся узел основной головы.
     * <br>
     * При обновлении головы обновляет своё состояние и копирует узёл основной головы под заданным номером.
     * <p>
     * Обновление итератора осуществляется через метод {@link #getNodesRealisation(int, Node, int) getNodes}.
     */
    private Node headIterator;
    private Node mainHead;
    private int iteratorSize = 0;
    private int mainSize = 0;



    //Получение размеров

    public int getListSize() {
        return mainSize;
    }

    public int getListSize(Node head) {
        return calculateListSize(head);
    }

    private int calculateListSize(Node head) {
        int count = 0;
        while (head != null) {
            head = head.nextNode;
            count++;
        }
        return count;
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

    //Удаление полного списка

    public ResultWrapper<Node> deleteList(Node head) {
        return deleteListRealisation(head);
    }

    public ResultWrapper<Node> deleteList() {
        ResultWrapper<Node> deleteListRealisationResult = deleteListRealisation(mainHead);
            mainHead = deleteListRealisationResult.getData();
        return ErrorHandler.failed(deleteListRealisationResult.getError());
    }

    private ResultWrapper<Node> deleteListRealisation(Node head) {
        if (head == mainHead) mainSize = 0;
        while (head != null) {
            Node tempHead = head;
            head = head.nextNode;
            tempHead.previousNode = null;
            tempHead.nextNode = null;
        }
        return ErrorHandler.success();
    }

    //Удаление через поиск по значению


        //Получение значения из узла

        public ResultWrapper<Integer> getValue(Node head) {
            if (head == null) return new ResultWrapper<>(null, ObjectErrors.EMPTY_OBJECT);
            return new ResultWrapper<>(head.data, ObjectErrors.NO_ERROR);
        }



    private ResultWrapper<LinkedList> findAndDeleteValue(int data, Node head) {
        while (head != null) {
            if (head.data == data) {
                ResultWrapper<Node> findAndDeleteResult = nodeDelete(head);
                if (findAndDeleteResult.getError() != ObjectErrors.NO_ERROR)
                    ErrorHandler.failed(findAndDeleteResult.getError());
                head = findAndDeleteResult.getData();
            }
            else
                head = head.nextNode;
        }
        return ErrorHandler.success();
    }

    //Удаление следующего элемента через поиск по значению

    public ResultWrapper<Node> findAndDeleteAfterValue(int data, Node head) {
        return findAndDeleteAfterValueRealisation(data, head);
    }

    public ResultWrapper<Node> findAndDeleteAfterValue(int data) {
        return ErrorHandler.failed(findAndDeleteAfterValueRealisation(data, mainHead).getError());
    }

    private ResultWrapper<Node> findAndDeleteAfterValueRealisation(int data, Node head) {
        Node copyHead = head;
        int count = 0;
        while (copyHead != null) {
            boolean readyToDelete = false;
            while (copyHead.data == data && copyHead.nextNode != null) {
                copyHead = copyHead.nextNode;
                readyToDelete = true;
                count++;
            }
            if (readyToDelete && copyHead.nextNode != null) {
                ResultWrapper<Node> findAndDeleteResult = nodeDelete(copyHead);
                if (findAndDeleteResult.getError() != ObjectErrors.NO_ERROR)
                    ErrorHandler.failed(findAndDeleteResult.getError());
                copyHead = findAndDeleteResult.getData();
            }
            else {
                copyHead = copyHead.nextNode;
                count++;
            }
        }
        return ErrorHandler.success();
    }

    //Удаление узла

    //TODO: Наелся говна с последним элементом. Убрать обнуление ссылки для удаления после элемента, в случае, если входящий элемент оказался последним
    private ResultWrapper<Node> nodeDelete(Node copyHead) {
        Node copyCopyHead = copyHead;
        if (copyHead.previousNode != null) {
            copyHead.previousNode.nextNode = copyHead.nextNode;
        }
        else {
            copyHead = copyHead.nextNode;
        }
        if (copyHead.nextNode != null) {
            copyHead.nextNode.previousNode = copyHead.previousNode;
        }
        //Изменить систему вычитания
        mainSize--;
        copyHead = copyHead.nextNode;
        copyCopyHead.previousNode = null;
        copyCopyHead.nextNode = null;
        return ErrorHandler.success(copyHead);
    }


    public ResultWrapper<Node> nodeSubstraction(Node head) {
        ResultWrapper<Node> NodeSubstractionResult = nodeSubstractionRealisation(mainHead, head);
        if (NodeSubstractionResult.getError() == ObjectErrors.NO_ERROR)
            head = NodeSubstractionResult.getData();
        return new ResultWrapper<>(head, NodeSubstractionResult.getError());
    }

    public ResultWrapper<Node> nodeSubstraction(Node head, Node headTwo) {
        return nodeSubstractionRealisation(head, headTwo);
    }

    private ResultWrapper<Node> nodeSubstractionRealisation(Node head, Node headTwo) {
        if (head == null || headTwo == null)
            return new ResultWrapper<>(null, ObjectErrors.EMPTY_OBJECT);
        ResultWrapper<Node> findNodesIdxResult = findNodesIdxRealisation(head, headTwo);
        if (findNodesIdxResult.getError() != ObjectErrors.NO_ERROR)
            return new ResultWrapper<>(null, findNodesIdxResult.getError());
        Node foundIdx = findNodesIdxResult.getData();

        int iteration = 0;
        while (foundIdx != null) {
            boolean readyToDelete = false;
            if (foundIdx.data != null) {
                int idx = foundIdx.data - iteration;
                int count = 0;
                Node copyHead = head;
                while (count < idx) {
                    count++;
                    copyHead = copyHead.nextNode;
                    readyToDelete = true;
                }
                if (readyToDelete && copyHead.nextNode != null)
                    nodeDelete(copyHead);
            }
            foundIdx = foundIdx.nextNode;
            iteration++;
        }
        return new ResultWrapper<>(head, ObjectErrors.NO_ERROR);
    }

    //Поиск индексов узлов через сравнение значений списков

    public ResultWrapper<Node> findNodesIdx(Node head, Node headTwo) {
        return findNodesIdxRealisation(head, headTwo);
    }

    public ResultWrapper<Node> findNodesIdx(Node head) {
        return findNodesIdxRealisation(mainHead, head);
    }

    private ResultWrapper<Node> findNodesIdxRealisation(Node head, Node headTwo) {
        ResultWrapper<Node> initializationResult = initializationRealisation(null);
        if (initializationResult.getError() != ObjectErrors.NO_ERROR)
            return new ResultWrapper<>(null, initializationResult.getError());
        Node idxList = initializationResult.getData();
        deleteRepeatIdx(headTwo);

        while (headTwo != null) {
            ResultWrapper<Node> initializationResultTwo = initializationRealisation(null);
            if (initializationResult.getError() != ObjectErrors.NO_ERROR)
                return new ResultWrapper<>(null, initializationResult.getError());
            Node tempIdx = initializationResultTwo.getData();

            ResultWrapper<Node> nodesIdxResult = nodesAfterValueIdx(head, headTwo.data, tempIdx);
            if (nodesIdxResult.getError() != ObjectErrors.NO_ERROR)
                return new ResultWrapper<>(null, nodesIdxResult.getError());
            tempIdx = nodesIdxResult.getData();

            while (tempIdx != null) {
                if (tempIdx.data == null) {
                    tempIdx = tempIdx.nextNode;
                    continue;
                }
                ResultWrapper<Node> addElementResult = addElement(tempIdx.data, idxList);
                if (addElementResult.getError() != ObjectErrors.NO_ERROR)
                    return new ResultWrapper<>(null, addElementResult.getError());
                idxList = addElementResult.getData();

                tempIdx = tempIdx.nextNode;
            }
            headTwo = headTwo.nextNode;
        }
        if (idxList == null) return new ResultWrapper<>(null, ObjectErrors.EMPTY_OBJECT);
        return new ResultWrapper<>(idxList, ObjectErrors.NO_ERROR);
    }

    //Удаление повторяющихся индексов

    private ResultWrapper<Node> deleteRepeatIdx(Node head) {
        while (head != null) {
            findAndDeleteValue(head.data, head.nextNode);
            head = head.nextNode;
        }
        return new ResultWrapper<>(null, ObjectErrors.NO_ERROR);
    }

    //Поиск индексов после найденного элемента

    private ResultWrapper<Node> nodesAfterValueIdx(Node head, int dataTwo, Node idxList) {
        int count = 0;
        while (head != null) {
            boolean readyToDelete = false;
            while (head.data == dataTwo && head.nextNode != null) {
                head = head.nextNode;
                readyToDelete = true;
                count++;
            }
            if (readyToDelete && head.nextNode != null) {
                ResultWrapper<Node> addResult = addElement(count, idxList);
                if (addResult.getError() != ObjectErrors.NO_ERROR)
                    return new ResultWrapper<>(null, addResult.getError());
                idxList = addResult.getData();

            }
            else {
                count++;
                head = head.nextNode;
            }
        }
        return new ResultWrapper<>(idxList, ObjectErrors.NO_ERROR);
    }

    //Поиск элемента по значению

    public ResultWrapper<Node> findNodes(int data, Node head) {
        return findNodesRealisation(data, head);
    }

    public ResultWrapper<Node> findNodes(int data) {
        return findNodesRealisation(data, mainHead);
    }

    public ResultWrapper<Node> findNodesRealisation(int data, Node head) {
        Node copyHead = head;
        ResultWrapper<Node> initializationResult = initialization(null);
        if (initializationResult.getError() != ObjectErrors.NO_ERROR)
            return new ResultWrapper<>(null, initializationResult.getError());
        Node nodesList = initializationResult.getData();

        while (copyHead != null) {
            Node copyNextCopyNode = copyHead.nextNode;
            if (copyHead.data == data) {
                if (!(nodesList.nextNode == null && nodesList.previousNode == null && nodesList.data == null)) {
                    nodesList.previousNode = copyHead;
                    copyHead.nextNode = nodesList;
                }
                else
                    copyHead.nextNode = null;
                nodesList = copyHead;
            }
            copyHead = copyNextCopyNode;
        }
        return new ResultWrapper<>(nodesList, ObjectErrors.NO_ERROR);
    }

        //Получение узла

        public ResultWrapper<Node> getNodes(int idx, Node head, int size) {
            return getNodesRealisation(idx, head, size);
        }

        public ResultWrapper<Node> getNodes(int idx) {
            return getNodesRealisation(idx, mainHead, mainSize);
        }

        private ResultWrapper<Node> getNodesRealisation(int idx, Node head, int size) {
            if (head == null) return new ResultWrapper<>(null, ObjectErrors.EMPTY_OBJECT);
            if (idx == size - 1) {
                headIterator = null;
            }
            if (headIterator == null) {
                int count = 0;
                headIterator = head;
                while (count < idx) {
                    headIterator = headIterator.nextNode;
                    count++;
                }
            }
            Node copyHeadForPrint = headIterator;
            headIterator = headIterator.nextNode;
            return new ResultWrapper<>(copyHeadForPrint, ObjectErrors.NO_ERROR);
        }

        //Разность списков. Удаление следующего за элементом узла


}

