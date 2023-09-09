package com.example.demo.myList;

import com.example.demo.myList.exception.IndexMoreThanLengthException;
import com.example.demo.myList.exception.NotFoundItemException;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.util.Arrays;
import java.util.Objects;

public class StringList implements MyList{

    private final int INIT_SIZE = 0;
    private Integer[] StringList = new Integer[INIT_SIZE];
    private int pointer = 0;


    public StringList() {
    }

    public MyList makeStringListForTests(Integer[] data, MyList list){
        for (Integer datum : data) {
            list.add(datum);
        }
        return list;
    }

    @Override
    public Integer add(Integer item) {
        if (this.StringList.length == 0) {
            resize(this.StringList.length + 1);
        }
        if (pointer == this.StringList.length) {
            grow();;
        }
        this.StringList[pointer++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        if (index > this.StringList.length - 1) {
            throw new IndexOutOfBoundsException("Индекс, на который вы хотите втиснуть указанный элемент, больше размера списка");
        }
        resize(this.StringList.length + 1);
        System.arraycopy(this.StringList, index, this.StringList, index + 1, pointer - index);
        this.StringList[index] = item;
        pointer++;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        if (index > this.StringList.length - 1) {
            throw new IndexOutOfBoundsException("Индекс, на который вы хотите поместить элемент, больше размера списка");
        }
        this.StringList[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
        boolean ItemContains = false;
        int i = 0;
        for (; i < this.StringList.length; i++) {
            if (item.equals(this.StringList[i])) {
                ItemContains = true;
                break;
            }
        }
        if (ItemContains) {
            System.arraycopy(this.StringList, i + 1, this.StringList, i, pointer - i - 1);
            pointer--;
            resize(this.StringList.length - 1);
        } else {
            throw new NotFoundItemException("Указанный элемент не найден в списке");
        }

        return item;
    }

    @Override
    public Integer remove(int index) {
        if (index > this.StringList.length - 1) {
            throw new IndexMoreThanLengthException("Указанный индекс больше чем размер массива");
        }
        System.arraycopy(this.StringList, index + 1, this.StringList, index, pointer - index - 1);
        pointer--;
        resize(this.StringList.length - 1);
        return this.StringList[index];
    }

    @Override
    public boolean contains(Integer item) {
        int min = 0;
        int max = this.StringList.length - 1;
        Integer[] arr = new Integer[this.StringList.length];
        System.arraycopy(this.StringList, 0, arr, 0, this.StringList.length);
        sortInsertion(arr);
        System.out.println(Arrays.toString(arr));

        while (min <= max) {
            int mid = (min + max) / 2;

            if (Objects.equals(item, arr[mid])) {
                return true;
            }

            if (item < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    @Override
    public int indexOf(Integer item) {
        boolean ItemContains = false;
        int i = 0;
        for (; i < this.StringList.length; i++) {
            if (item.equals(this.StringList[i])) {
                ItemContains = true;
                break;
            }
        }
        if (ItemContains) {
            return i;
        } else {
            return -1;
        }
    }

    @Override
    public int lastIndexOf(Integer item) {
        boolean ItemContains = false;
        int i = this.StringList.length - 1;
        for (; i > -1; i--) {
            if (item.equals(this.StringList[i])) {
                ItemContains = true;
                break;
            }
        }
        if (ItemContains) {
            return i;
        } else {
            return -1;
        }
    }

    @Override
    public Integer get(int index) {
        if (index > this.StringList.length - 1) {
            throw new IndexMoreThanLengthException("Указанный индекс больше чем размер массива");
        }
        return this.StringList[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringList)) return false;
        StringList that = (StringList) o;
        return Arrays.equals(StringList, that.StringList);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(StringList);
    }

    @Override
    public int size() {
        return this.StringList.length;
    }

    @Override
    public boolean isEmpty() {
        return this.StringList.length == 0;
    }

    @Override
    public void clear() {
        this.StringList = new Integer[INIT_SIZE];
        pointer = 0;
    }

    @Override
    public Integer[] toArray() {
        Integer[] arr = new Integer[pointer];
        System.arraycopy(this.StringList, 0, arr, 0, pointer);
        return arr;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.StringList);
    }

    private void resize(int newLength) {
        Integer[] newArray = new Integer[newLength];
        System.arraycopy(this.StringList, 0, newArray, 0, pointer);
        this.StringList = newArray;
    }

    // показало 9 милисекунд при объеме данных в 1000
    private void sortBuble(Integer[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swapElements(arr, j, j + 1);
                }
            }
        }
    }
    // показало 5 милисекунд при объеме данных в 1000
    private void sortSelection(Integer[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(arr, i, minElementIndex);
        }
    }
    // показало 4 милисекунд при объеме данных в 1000 - исполльзую его
    private void sortInsertion(Integer[] arr){
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

    private void grow(){
        StringList = Arrays.copyOf(StringList, StringList.length * 2);
    }

    private static void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }
}