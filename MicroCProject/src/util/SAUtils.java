package util;

import java.util.*;
import models.*;
import business.*;

public static class SAUtils {

    private static Map<Tuple<SignType, SignType>, ArrayList<SignType>> plusTransferFunction;
    private static Map<Tuple<SignType, SignType>, ArrayList<SignType>> minusTransferFunction;
    private static Map<Tuple<SignType, SignType>, ArrayList<SignType>> productTransferFunction;
    private static Map<Tuple<SignType, SignType>, ArrayList<SignType>> divisionTransferFunction;
    private static Map<SignType, ArrayList<SignType>> unaryMinusTransferFunction;

    private static ArrayList<SignType> arrayListPlus;
    private static ArrayList<SignType> arrayListMinus;
    private static ArrayList<SignType> arrayListZero;
    private static ArrayList<SignType> arrayListIllegal;
    private static ArrayList<SignType> arrayListPlusMinusZero;

    private static void createSignArrayLists() {
        arrayListPlus = new ArrayList<SignType>();
        arrayListPlus.add(SignType.PLUS);

        arrayListMinus = new ArrayList<SignType>();
        arrayListMinus.add(SignType.MINUS);

        arrayListZero = new ArrayList<SignType>();
        arrayListZero.add(SignType.ZERO);

        arrayListIllegal = new ArrayList<SignType>();
        arrayListIllegal.add(SignType.ILLEGAL);

        arrayListPlusMinusZero = new ArrayList<SignType>();
        arrayListPlusMinusZero.add(SignType.PLUS);
        arrayListPlusMinusZero.add(SignType.ZERO);
        arrayListPlusMinusZero.add(SignType.MINUS);
    }

    private static void initializePlusTransferFunction()
    {
        plusTransferFunction = new HashMap<Tuple<SignType, SignType>, ArrayList<SignType>>();
        plusTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.PLUS), arrayListPlus);
        plusTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.ZERO), arrayListPlus);
        plusTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.MINUS), arrayListPlusMinusZero);
        plusTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.PLUS), arrayListPlus);
        plusTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.ZERO), arrayListZero);
        plusTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.MINUS), arrayListMinus);
        plusTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.PLUS), arrayListPlusMinusZero);
        plusTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.ZERO), arrayListMinus);
        plusTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.MINUS), arrayListMinus);
    }

    private static void initializeMinusTransferFunction()
    {
        minusTransferFunction = new HashMap<Tuple<SignType, SignType>, ArrayList<SignType>>();
        minusTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.PLUS), arrayListPlusMinusZero);
        minusTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.ZERO), arrayListPlus);
        minusTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.MINUS), arrayListPlus);
        minusTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.PLUS), arrayListMinus);
        minusTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.ZERO), arrayListZero);
        minusTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.MINUS), arrayListPlus);
        minusTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.PLUS), arrayListMinus);
        minusTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.ZERO), arrayListMinus);
        minusTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.MINUS), arrayListPlusMinusZero);
    }

    private static void initializeProductTransferFunction()
    {
        productTransferFunction = new HashMap<Tuple<SignType, SignType>, ArrayList<SignType>>();
        productTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.PLUS), arrayListPlus);
        productTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.ZERO), arrayListZero);
        productTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.MINUS), arrayListMinus);
        productTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.PLUS), arrayListZero);
        productTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.ZERO), arrayListZero);
        productTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.MINUS), arrayListZero);
        productTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.PLUS), arrayListMinus);
        productTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.ZERO), arrayListZero);
        productTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.MINUS), arrayListPlus);
    }

    private static void initializeDivisionTransferFunction()
    {
        divisionTransferFunction = new HashMap<Tuple<SignType, SignType>, ArrayList<SignType>>();
        divisionTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.PLUS), arrayListPlus);
        divisionTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.ZERO), arrayListIllegal);
        divisionTransferFunction.put(new Tuple<SignType, SignType>(SignType.PLUS, SignType.MINUS), arrayListMinus);
        divisionTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.PLUS), arrayListZero);
        divisionTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.ZERO), arrayListIllegal);
        divisionTransferFunction.put(new Tuple<SignType, SignType>(SignType.ZERO, SignType.MINUS), arrayListZero);
        divisionTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.PLUS), arrayListMinus);
        divisionTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.ZERO), arrayListIllegal);
        divisionTransferFunction.put(new Tuple<SignType, SignType>(SignType.MINUS, SignType.MINUS), arrayListPlus);
    }

    private static void initializeUnaryMinusTransferFunction() {
        unaryMinusTransferFunction = new HashMap<SignType, ArrayList<SignType>>();
        unaryMinusTransferFunction.put(SignType.PLUS, arrayListMinus);
        divisionTransferFunction.put(SignType.ZERO, arrayListZero);
        divisionTransferFunction.put(SignType.MINUS, arrayListPlus);
    }

    public static void initializeTransferFunctions() {
        createSignArrayLists();
        initializePlusTransferFunction();
        initializeMinusTransferFunction();
        initializeProductTransferFunction();
        initializeDivisionTransferFunction();
        initializeUnaryMinusTransferFunction();
    }

    public static Map<Tuple<SignType, SignType>, ArrayList<SignType>> getPlusTransferFunction() {
        return plusTransferFunction;
    }

    public static Map<Tuple<SignType, SignType>, ArrayList<SignType>> getMinusTransferFunction() {
        return minusTransferFunction;
    }

    public static Map<Tuple<SignType, SignType>, ArrayList<SignType>> getProductTransferFunction() {
        return productTransferFunction;
    }

    public static Map<Tuple<SignType, SignType>, ArrayList<SignType>> getDivisionTransferFunction() {
        return divisionTransferFunction;
    }

    public static Map<SignType, ArrayList<SignType>> getUnaryMinusTransferFunction() {
        return unaryMinusTransferFunction;
    }

    public static ArrayList<SignType> getArrayListPlus() {
        return arrayListPlus;
    }

    public static ArrayList<SignType> getArrayListMinus() {
        return arrayListMinus;
    }

    public static ArrayList<SignType> getArrayListZero() {
        return arrayListZero;
    }

    public static ArrayList<SignType> getArrayListIllegal() {
        return arrayListIllegal;
    }

    public static ArrayList<SignType> getArrayListPlusMinusZero() {
        return arrayListPlusMinusZero;
    }

}