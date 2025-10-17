package services;

import errors.ErrorHandler;
import errors.ObjectErrors;
import resultwrapper.ResultWrapper;

public class MainHeadManipulation {

    LinkedListUtils linkedList = new LinkedListUtils();

    public ResultWrapper<LinkedList.Node> findAndDeleteValue(int data) {
        ResultWrapper<LinkedList.Node> findAndDeleteValueResult = linkedList.findAndDeleteAfterValueRealisation(data, linkedList.mainHead, true);
        if (findAndDeleteValueResult.getError() == ObjectErrors.NO_ERROR)
            return ErrorHandler.failed(findAndDeleteValueResult.getError());
        return ErrorHandler.success();
    }


    public ResultWrapper<LinkedList.Node> findAndDeleteAfterValue(int data) {
        ResultWrapper<LinkedList.Node> findAndDeleteResult = linkedList.findAndDeleteAfterValueRealisation(data, linkedList.mainHead, true);
        if (findAndDeleteResult.getError() == ObjectErrors.NO_ERROR)
            return ErrorHandler.failed(linkedList.findAndDeleteAfterValueRealisation(data, linkedList.mainHead, true).getError());
        return ErrorHandler.success();
    }

    public ResultWrapper<LinkedList.Node> nodeSubstraction(LinkedList.Node head) {
        ResultWrapper<LinkedList.Node> nodeSubstractionResult = linkedList.nodeSubstractionRealisation(linkedList.mainHead, head, true);
        if (nodeSubstractionResult.getError() == ObjectErrors.NO_ERROR)
            return ErrorHandler.failed(nodeSubstractionResult.getError());
        return ErrorHandler.success();
    }

    public ResultWrapper<LinkedList.Node> findNodesIdx(LinkedList.Node head) {
        return linkedList.findNodesIdxRealisation(linkedList.mainHead, head);
    }
}
