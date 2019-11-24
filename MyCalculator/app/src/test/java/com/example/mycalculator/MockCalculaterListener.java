package com.example.mycalculator;

class MockCalculaterListener implements CalculatorListener {

    int result;
    @Override
    public void  onResultCalculated(int result){
        this.result = result;

    }
}
