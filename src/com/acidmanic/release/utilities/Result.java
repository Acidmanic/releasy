/*
 * Copyright (C) 2020 Acidmanic
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.acidmanic.release.utilities;

/**
 *
 * @author Acidmanic
 * @param <T>
 */
public class Result<T> {
    
    private T value;
    
    private String message;
    
    private boolean success;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static <Tvalue> Result<Tvalue> success(Tvalue value){
        
        Result<Tvalue> result = new Result<>();
        
        result.setMessage("");
        
        result.setValue(value);
        
        result.setSuccess(true);
        
        return result;
    }
    
    public static <T> Result<T> fail(String message){
        
        Result<T> result = new Result<>();
        
        result.setMessage(message);
        
        result.setValue(null);
        
        result.setSuccess(false);
        
        return result;
    }
    
    
}
