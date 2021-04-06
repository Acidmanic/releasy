/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.utilities;

/**
 *
 * @author diego
 * @param <TLeft> Type of first field
 * @param <TRight> Type of second field
 */
public class Pair<TLeft,TRight> {
    
    
    private TLeft left;
    private TRight right;

    public Pair(TLeft left, TRight right) {
        this.left = left;
        this.right = right;
    }

    public Pair() {
    }

    public TLeft getLeft() {
        return left;
    }

    public void setLeft(TLeft left) {
        this.left = left;
    }

    public TRight getRight() {
        return right;
    }

    public void setRight(TRight right) {
        this.right = right;
    }
    
    
    
}
