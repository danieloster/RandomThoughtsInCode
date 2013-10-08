package experiment;

import experiment.dto.Dto;

import java.beans.IntrospectionException;

public class Test {

    public static void main(String[] args) throws IllegalArgumentException, IntrospectionException {
        Customer customer = Dto.create(Customer.class);

    }

}
