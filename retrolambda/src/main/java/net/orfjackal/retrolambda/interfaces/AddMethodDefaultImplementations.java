// Copyright © 2013-2015 Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package net.orfjackal.retrolambda.interfaces;

import net.orfjackal.retrolambda.util.Bytecode;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class AddMethodDefaultImplementations extends ClassVisitor {

    private final ClassHierarchyAnalyzer analyzer;
    private String className;

    public AddMethodDefaultImplementations(ClassVisitor next, ClassHierarchyAnalyzer analyzer) {
        super(ASM5, next);
        this.analyzer = analyzer;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public void visitEnd() {
        for (MethodInfo method : analyzer.getDefaultMethods(Type.getObjectType(className))) {
            Bytecode.generateDelegateMethod(cv,
                    ACC_PUBLIC | ACC_SYNTHETIC,
                    method.toMethodRef().toHandle(H_INVOKEVIRTUAL),
                    method.getDefaultMethodImpl().toHandle(H_INVOKESTATIC));
        }
        super.visitEnd();
    }
}
