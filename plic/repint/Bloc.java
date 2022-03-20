package repint;

import exceptions.ExceptionSemantique;

import java.util.ArrayList;
import java.util.List;

public class Bloc {

    private final List<Instruction> instructions;

    public Bloc() {
        this.instructions = new ArrayList<>();
    }

    public void ajouterInstr(Instruction instruction) {
        this.instructions.add(instruction);
    }

    public void verifier() throws ExceptionSemantique {
        for (Instruction instruction : this.instructions) {
            instruction.verifier();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Bloc(\n");
        for (Instruction instruction : instructions) {
            builder.append("    ").append(instruction).append("\n");
        }
        return builder.append(")").toString();
    }

    public String toMips() {
        StringBuilder builder = new StringBuilder();
    }
}
