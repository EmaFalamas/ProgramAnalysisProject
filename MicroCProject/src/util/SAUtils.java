package util;

import java.util.*;
import models.*;
import business.*;

public class SAUtils {

    private static Map<String, ArrayList<String>> plusTransferFunction;
    private static Map<String, ArrayList<String>> minusTransferFunction;
    private static Map<String, ArrayList<String>> productTransferFunction;
    private static Map<String, ArrayList<String>> divisionTransferFunction;
    private static Map<String, ArrayList<String>> unaryMinusTransferFunction;

    private static ArrayList<String> arrayListPlus;
    private static ArrayList<String> arrayListMinus;
    private static ArrayList<String> arrayListZero;
    private static ArrayList<String> arrayListIllegal;
    private static ArrayList<String> arrayListPlusMinusZero;

    private static void createSignArrayLists() {
        arrayListPlus = new ArrayList<String>();
        arrayListPlus.add("+");

        arrayListMinus = new ArrayList<String>();
        arrayListMinus.add("-");

        arrayListZero = new ArrayList<String>();
        arrayListZero.add("0");

        arrayListIllegal = new ArrayList<String>();
        arrayListIllegal.add("illegal");

        arrayListPlusMinusZero = new ArrayList<String>();
        arrayListPlusMinusZero.add("+");
        arrayListPlusMinusZero.add("0");
        arrayListPlusMinusZero.add("-");
    }

    private static void initializePlusTransferFunction()
    {
        plusTransferFunction = new HashMap<String, ArrayList<String>>();
        plusTransferFunction.put("++", arrayListPlus);
        plusTransferFunction.put("+0", arrayListPlus);
        plusTransferFunction.put("+-", arrayListPlusMinusZero);
        plusTransferFunction.put("0+", arrayListPlus);
        plusTransferFunction.put("00", arrayListZero);
        plusTransferFunction.put("0-", arrayListMinus);
        plusTransferFunction.put("-+", arrayListPlusMinusZero);
        plusTransferFunction.put("-0", arrayListMinus);
        plusTransferFunction.put("--", arrayListMinus);
    }

    private static void initializeMinusTransferFunction()
    {
        minusTransferFunction = new HashMap<String, ArrayList<String>>();
        minusTransferFunction.put("++", arrayListPlusMinusZero);
        minusTransferFunction.put("+0", arrayListPlus);
        minusTransferFunction.put("+-", arrayListPlus);
        minusTransferFunction.put("0+", arrayListMinus);
        minusTransferFunction.put("00", arrayListZero);
        minusTransferFunction.put("0-", arrayListPlus);
        minusTransferFunction.put("-+", arrayListMinus);
        minusTransferFunction.put("-0", arrayListMinus);
        minusTransferFunction.put("--", arrayListPlusMinusZero);
    }

    private static void initializeProductTransferFunction()
    {
        productTransferFunction = new HashMap<String, ArrayList<String>>();
        productTransferFunction.put("++", arrayListPlus);
        productTransferFunction.put("+0", arrayListZero);
        productTransferFunction.put("+-", arrayListMinus);
        productTransferFunction.put("0+", arrayListZero);
        productTransferFunction.put("00", arrayListZero);
        productTransferFunction.put("0-", arrayListZero);
        productTransferFunction.put("-+", arrayListMinus);
        productTransferFunction.put("-0", arrayListZero);
        productTransferFunction.put("--", arrayListPlus);
    }

    private static void initializeDivisionTransferFunction()
    {
        divisionTransferFunction = new HashMap<String, ArrayList<String>>();
        divisionTransferFunction.put("++", arrayListPlus);
        divisionTransferFunction.put("+0", arrayListIllegal);
        divisionTransferFunction.put("+-", arrayListMinus);
        divisionTransferFunction.put("0+", arrayListZero);
        divisionTransferFunction.put("00", arrayListIllegal);
        divisionTransferFunction.put("0-", arrayListZero);
        divisionTransferFunction.put("-+", arrayListMinus);
        divisionTransferFunction.put("-0", arrayListIllegal);
        divisionTransferFunction.put("--", arrayListPlus);
    }

    private static void initializeUnaryMinusTransferFunction() {
        unaryMinusTransferFunction = new HashMap<String, ArrayList<String>>();
        unaryMinusTransferFunction.put("+", arrayListMinus);
        unaryMinusTransferFunction.put("0", arrayListZero);
        unaryMinusTransferFunction.put("-", arrayListPlus);
    }

    public static void initializeTransferFunctions() {
        createSignArrayLists();
        initializePlusTransferFunction();
        initializeMinusTransferFunction();
        initializeProductTransferFunction();
        initializeDivisionTransferFunction();
        initializeUnaryMinusTransferFunction();
    }

    public static Map<String, ArrayList<String>> getPlusTransferFunction() {
        return plusTransferFunction;
    }

    public static Map<String, ArrayList<String>> getMinusTransferFunction() {
        return minusTransferFunction;
    }

    public static Map<String, ArrayList<String>> getProductTransferFunction() {
        return productTransferFunction;
    }

    public static Map<String, ArrayList<String>> getDivisionTransferFunction() {
        return divisionTransferFunction;
    }

    public static Map<String, ArrayList<String>> getUnaryMinusTransferFunction() {
        return unaryMinusTransferFunction;
    }

    public static ArrayList<String> getArrayListPlus() {
        return arrayListPlus;
    }

    public static ArrayList<String> getArrayListMinus() {
        return arrayListMinus;
    }

    public static ArrayList<String> getArrayListZero() {
        return arrayListZero;
    }

    public static ArrayList<String> getArrayListIllegal() {
        return arrayListIllegal;
    }

    public static ArrayList<String> getArrayListPlusMinusZero() {
        return arrayListPlusMinusZero;
    }
}