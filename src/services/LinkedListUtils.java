package services;

import errors.ErrorHandler;
import errors.ObjectErrors;
import resultwrapper.ResultWrapper;

public class LinkedListUtils extends LinkedList {

    //Получение узла

    public ResultWrapper<Node> findAndDeleteValue(int data, Node head) {
        return findAndDeleteAfterValueRealisation(data, head, false);
    }

    private int calculateListSize(Node head) {
        int count = 0;
        while (head != null) {
            head = head.nextNode;
            count++;
        }
        return count;
    }


    ResultWrapper<Node> findAndDeleteValueRealisation(int data, Node head, boolean isMain) {
        Node copyHead = head;
        while (copyHead != null) {
            if (copyHead.data == data) {
                ResultWrapper<Node> findAndDeleteResult = nodeDeleteRealisation(copyHead, isMain);
                if (findAndDeleteResult.getError() != ObjectErrors.NO_ERROR)
                    ErrorHandler.failed(findAndDeleteResult.getError());
                copyHead = findAndDeleteResult.getData();
            }
            else
                copyHead = copyHead.nextNode;
        }
        return ErrorHandler.success(head);
    }

    //Удаление следующего элемента через поиск по значению

    public ResultWrapper<Node> findAndDeleteAfterValue(int data, Node head) {
        return findAndDeleteAfterValueRealisation(data, head, false);
    }

    ResultWrapper<Node> findAndDeleteAfterValueRealisation(int data, Node head, boolean isMain) {
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
                ResultWrapper<Node> findAndDeleteResult = nodeDeleteRealisation(copyHead, isMain);
                if (findAndDeleteResult.getError() != ObjectErrors.NO_ERROR)
                    ErrorHandler.failed(findAndDeleteResult.getError());
                copyHead = findAndDeleteResult.getData();
            }
            else {
                copyHead = copyHead.nextNode;
                count++;
            }
        }
        return ErrorHandler.success(head);
    }

    //Удаление узла

    public ResultWrapper<Node> nodeDelete(Node head) {
        return nodeDeleteRealisation(head, false);
    }

    //TODO: Наелся говна с последним элементом. Убрать обнуление ссылки для удаления после элемента, в случае, если входящий элемент оказался последним
    private ResultWrapper<Node> nodeDeleteRealisation(Node copyHead, boolean isMain) {
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
        if (isMain) mainSize--;
        copyHead = copyHead.nextNode;
        copyCopyHead.previousNode = null;
        copyCopyHead.nextNode = null;
        return ErrorHandler.success(copyHead);
    }

    public ResultWrapper<Node> nodeSubstraction(Node head, Node headTwo) {
        return nodeSubstractionRealisation(head, headTwo, false);
    }

    ResultWrapper<Node> nodeSubstractionRealisation(Node head, Node headTwo, boolean isMain) {
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
                    nodeDeleteRealisation(copyHead, isMain);
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

    ResultWrapper<Node> findNodesIdxRealisation(Node head, Node headTwo) {
        ResultWrapper<Node> initializationResult = initialization(null);
        if (initializationResult.getError() != ObjectErrors.NO_ERROR)
            return new ResultWrapper<>(null, initializationResult.getError());
        Node idxList = initializationResult.getData();
        deleteRepeatNodes(headTwo);

        while (headTwo != null) {
            ResultWrapper<Node> initializationResultTwo = initialization(null);
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

    public ResultWrapper<Node> deleteRepeatNodes(Node head) {
        return deleteRepeatNodesRealisation(head, false);
    }

    public ResultWrapper<Node> deleteRepeatNodes() {
        return deleteRepeatNodesRealisation(mainHead, true);
    }

    private ResultWrapper<Node> deleteRepeatNodesRealisation(Node head, boolean isMain) {
        Node headCopy = head;
        while (head != null) {
            findAndDeleteValueRealisation(headCopy.data, headCopy.nextNode, isMain);
            headCopy = headCopy.nextNode;
        }
        return ErrorHandler.success(head);
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

    private ResultWrapper<Node> getNodesOnIdxRealisation(int idx, Node head, int size, Node headIterator) {
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

}
