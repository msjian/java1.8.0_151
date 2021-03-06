package jdk.internal.org.objectweb.asm.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;

public class GeneratorAdapter
  extends LocalVariablesSorter
{
  private static final String CLDESC = "Ljava/lang/Class;";
  private static final Type BYTE_TYPE = Type.getObjectType("java/lang/Byte");
  private static final Type BOOLEAN_TYPE = Type.getObjectType("java/lang/Boolean");
  private static final Type SHORT_TYPE = Type.getObjectType("java/lang/Short");
  private static final Type CHARACTER_TYPE = Type.getObjectType("java/lang/Character");
  private static final Type INTEGER_TYPE = Type.getObjectType("java/lang/Integer");
  private static final Type FLOAT_TYPE = Type.getObjectType("java/lang/Float");
  private static final Type LONG_TYPE = Type.getObjectType("java/lang/Long");
  private static final Type DOUBLE_TYPE = Type.getObjectType("java/lang/Double");
  private static final Type NUMBER_TYPE = Type.getObjectType("java/lang/Number");
  private static final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
  private static final Method BOOLEAN_VALUE = Method.getMethod("boolean booleanValue()");
  private static final Method CHAR_VALUE = Method.getMethod("char charValue()");
  private static final Method INT_VALUE = Method.getMethod("int intValue()");
  private static final Method FLOAT_VALUE = Method.getMethod("float floatValue()");
  private static final Method LONG_VALUE = Method.getMethod("long longValue()");
  private static final Method DOUBLE_VALUE = Method.getMethod("double doubleValue()");
  public static final int ADD = 96;
  public static final int SUB = 100;
  public static final int MUL = 104;
  public static final int DIV = 108;
  public static final int REM = 112;
  public static final int NEG = 116;
  public static final int SHL = 120;
  public static final int SHR = 122;
  public static final int USHR = 124;
  public static final int AND = 126;
  public static final int OR = 128;
  public static final int XOR = 130;
  public static final int EQ = 153;
  public static final int NE = 154;
  public static final int LT = 155;
  public static final int GE = 156;
  public static final int GT = 157;
  public static final int LE = 158;
  private final int access;
  private final Type returnType;
  private final Type[] argumentTypes;
  private final List<Type> localTypes = new ArrayList();
  
  public GeneratorAdapter(MethodVisitor paramMethodVisitor, int paramInt, String paramString1, String paramString2)
  {
    this(327680, paramMethodVisitor, paramInt, paramString1, paramString2);
    if (getClass() != GeneratorAdapter.class) {
      throw new IllegalStateException();
    }
  }
  
  protected GeneratorAdapter(int paramInt1, MethodVisitor paramMethodVisitor, int paramInt2, String paramString1, String paramString2)
  {
    super(paramInt1, paramInt2, paramString2, paramMethodVisitor);
    access = paramInt2;
    returnType = Type.getReturnType(paramString2);
    argumentTypes = Type.getArgumentTypes(paramString2);
  }
  
  public GeneratorAdapter(int paramInt, Method paramMethod, MethodVisitor paramMethodVisitor)
  {
    this(paramMethodVisitor, paramInt, null, paramMethod.getDescriptor());
  }
  
  public GeneratorAdapter(int paramInt, Method paramMethod, String paramString, Type[] paramArrayOfType, ClassVisitor paramClassVisitor)
  {
    this(paramInt, paramMethod, paramClassVisitor.visitMethod(paramInt, paramMethod.getName(), paramMethod.getDescriptor(), paramString, getInternalNames(paramArrayOfType)));
  }
  
  private static String[] getInternalNames(Type[] paramArrayOfType)
  {
    if (paramArrayOfType == null) {
      return null;
    }
    String[] arrayOfString = new String[paramArrayOfType.length];
    for (int i = 0; i < arrayOfString.length; i++) {
      arrayOfString[i] = paramArrayOfType[i].getInternalName();
    }
    return arrayOfString;
  }
  
  public void push(boolean paramBoolean)
  {
    push(paramBoolean ? 1 : 0);
  }
  
  public void push(int paramInt)
  {
    if ((paramInt >= -1) && (paramInt <= 5)) {
      mv.visitInsn(3 + paramInt);
    } else if ((paramInt >= -128) && (paramInt <= 127)) {
      mv.visitIntInsn(16, paramInt);
    } else if ((paramInt >= 32768) && (paramInt <= 32767)) {
      mv.visitIntInsn(17, paramInt);
    } else {
      mv.visitLdcInsn(Integer.valueOf(paramInt));
    }
  }
  
  public void push(long paramLong)
  {
    if ((paramLong == 0L) || (paramLong == 1L)) {
      mv.visitInsn(9 + (int)paramLong);
    } else {
      mv.visitLdcInsn(Long.valueOf(paramLong));
    }
  }
  
  public void push(float paramFloat)
  {
    int i = Float.floatToIntBits(paramFloat);
    if ((i == 0L) || (i == 1065353216) || (i == 1073741824)) {
      mv.visitInsn(11 + (int)paramFloat);
    } else {
      mv.visitLdcInsn(Float.valueOf(paramFloat));
    }
  }
  
  public void push(double paramDouble)
  {
    long l = Double.doubleToLongBits(paramDouble);
    if ((l == 0L) || (l == 4607182418800017408L)) {
      mv.visitInsn(14 + (int)paramDouble);
    } else {
      mv.visitLdcInsn(Double.valueOf(paramDouble));
    }
  }
  
  public void push(String paramString)
  {
    if (paramString == null) {
      mv.visitInsn(1);
    } else {
      mv.visitLdcInsn(paramString);
    }
  }
  
  public void push(Type paramType)
  {
    if (paramType == null) {
      mv.visitInsn(1);
    } else {
      switch (paramType.getSort())
      {
      case 1: 
        mv.visitFieldInsn(178, "java/lang/Boolean", "TYPE", "Ljava/lang/Class;");
        break;
      case 2: 
        mv.visitFieldInsn(178, "java/lang/Character", "TYPE", "Ljava/lang/Class;");
        break;
      case 3: 
        mv.visitFieldInsn(178, "java/lang/Byte", "TYPE", "Ljava/lang/Class;");
        break;
      case 4: 
        mv.visitFieldInsn(178, "java/lang/Short", "TYPE", "Ljava/lang/Class;");
        break;
      case 5: 
        mv.visitFieldInsn(178, "java/lang/Integer", "TYPE", "Ljava/lang/Class;");
        break;
      case 6: 
        mv.visitFieldInsn(178, "java/lang/Float", "TYPE", "Ljava/lang/Class;");
        break;
      case 7: 
        mv.visitFieldInsn(178, "java/lang/Long", "TYPE", "Ljava/lang/Class;");
        break;
      case 8: 
        mv.visitFieldInsn(178, "java/lang/Double", "TYPE", "Ljava/lang/Class;");
        break;
      default: 
        mv.visitLdcInsn(paramType);
      }
    }
  }
  
  public void push(Handle paramHandle)
  {
    mv.visitLdcInsn(paramHandle);
  }
  
  private int getArgIndex(int paramInt)
  {
    int i = (access & 0x8) == 0 ? 1 : 0;
    for (int j = 0; j < paramInt; j++) {
      i += argumentTypes[j].getSize();
    }
    return i;
  }
  
  private void loadInsn(Type paramType, int paramInt)
  {
    mv.visitVarInsn(paramType.getOpcode(21), paramInt);
  }
  
  private void storeInsn(Type paramType, int paramInt)
  {
    mv.visitVarInsn(paramType.getOpcode(54), paramInt);
  }
  
  public void loadThis()
  {
    if ((access & 0x8) != 0) {
      throw new IllegalStateException("no 'this' pointer within static method");
    }
    mv.visitVarInsn(25, 0);
  }
  
  public void loadArg(int paramInt)
  {
    loadInsn(argumentTypes[paramInt], getArgIndex(paramInt));
  }
  
  public void loadArgs(int paramInt1, int paramInt2)
  {
    int i = getArgIndex(paramInt1);
    for (int j = 0; j < paramInt2; j++)
    {
      Type localType = argumentTypes[(paramInt1 + j)];
      loadInsn(localType, i);
      i += localType.getSize();
    }
  }
  
  public void loadArgs()
  {
    loadArgs(0, argumentTypes.length);
  }
  
  public void loadArgArray()
  {
    push(argumentTypes.length);
    newArray(OBJECT_TYPE);
    for (int i = 0; i < argumentTypes.length; i++)
    {
      dup();
      push(i);
      loadArg(i);
      box(argumentTypes[i]);
      arrayStore(OBJECT_TYPE);
    }
  }
  
  public void storeArg(int paramInt)
  {
    storeInsn(argumentTypes[paramInt], getArgIndex(paramInt));
  }
  
  public Type getLocalType(int paramInt)
  {
    return (Type)localTypes.get(paramInt - firstLocal);
  }
  
  protected void setLocalType(int paramInt, Type paramType)
  {
    int i = paramInt - firstLocal;
    while (localTypes.size() < i + 1) {
      localTypes.add(null);
    }
    localTypes.set(i, paramType);
  }
  
  public void loadLocal(int paramInt)
  {
    loadInsn(getLocalType(paramInt), paramInt);
  }
  
  public void loadLocal(int paramInt, Type paramType)
  {
    setLocalType(paramInt, paramType);
    loadInsn(paramType, paramInt);
  }
  
  public void storeLocal(int paramInt)
  {
    storeInsn(getLocalType(paramInt), paramInt);
  }
  
  public void storeLocal(int paramInt, Type paramType)
  {
    setLocalType(paramInt, paramType);
    storeInsn(paramType, paramInt);
  }
  
  public void arrayLoad(Type paramType)
  {
    mv.visitInsn(paramType.getOpcode(46));
  }
  
  public void arrayStore(Type paramType)
  {
    mv.visitInsn(paramType.getOpcode(79));
  }
  
  public void pop()
  {
    mv.visitInsn(87);
  }
  
  public void pop2()
  {
    mv.visitInsn(88);
  }
  
  public void dup()
  {
    mv.visitInsn(89);
  }
  
  public void dup2()
  {
    mv.visitInsn(92);
  }
  
  public void dupX1()
  {
    mv.visitInsn(90);
  }
  
  public void dupX2()
  {
    mv.visitInsn(91);
  }
  
  public void dup2X1()
  {
    mv.visitInsn(93);
  }
  
  public void dup2X2()
  {
    mv.visitInsn(94);
  }
  
  public void swap()
  {
    mv.visitInsn(95);
  }
  
  public void swap(Type paramType1, Type paramType2)
  {
    if (paramType2.getSize() == 1)
    {
      if (paramType1.getSize() == 1)
      {
        swap();
      }
      else
      {
        dupX2();
        pop();
      }
    }
    else if (paramType1.getSize() == 1)
    {
      dup2X1();
      pop2();
    }
    else
    {
      dup2X2();
      pop2();
    }
  }
  
  public void math(int paramInt, Type paramType)
  {
    mv.visitInsn(paramType.getOpcode(paramInt));
  }
  
  public void not()
  {
    mv.visitInsn(4);
    mv.visitInsn(130);
  }
  
  public void iinc(int paramInt1, int paramInt2)
  {
    mv.visitIincInsn(paramInt1, paramInt2);
  }
  
  public void cast(Type paramType1, Type paramType2)
  {
    if (paramType1 != paramType2) {
      if (paramType1 == Type.DOUBLE_TYPE)
      {
        if (paramType2 == Type.FLOAT_TYPE)
        {
          mv.visitInsn(144);
        }
        else if (paramType2 == Type.LONG_TYPE)
        {
          mv.visitInsn(143);
        }
        else
        {
          mv.visitInsn(142);
          cast(Type.INT_TYPE, paramType2);
        }
      }
      else if (paramType1 == Type.FLOAT_TYPE)
      {
        if (paramType2 == Type.DOUBLE_TYPE)
        {
          mv.visitInsn(141);
        }
        else if (paramType2 == Type.LONG_TYPE)
        {
          mv.visitInsn(140);
        }
        else
        {
          mv.visitInsn(139);
          cast(Type.INT_TYPE, paramType2);
        }
      }
      else if (paramType1 == Type.LONG_TYPE)
      {
        if (paramType2 == Type.DOUBLE_TYPE)
        {
          mv.visitInsn(138);
        }
        else if (paramType2 == Type.FLOAT_TYPE)
        {
          mv.visitInsn(137);
        }
        else
        {
          mv.visitInsn(136);
          cast(Type.INT_TYPE, paramType2);
        }
      }
      else if (paramType2 == Type.BYTE_TYPE) {
        mv.visitInsn(145);
      } else if (paramType2 == Type.CHAR_TYPE) {
        mv.visitInsn(146);
      } else if (paramType2 == Type.DOUBLE_TYPE) {
        mv.visitInsn(135);
      } else if (paramType2 == Type.FLOAT_TYPE) {
        mv.visitInsn(134);
      } else if (paramType2 == Type.LONG_TYPE) {
        mv.visitInsn(133);
      } else if (paramType2 == Type.SHORT_TYPE) {
        mv.visitInsn(147);
      }
    }
  }
  
  private static Type getBoxedType(Type paramType)
  {
    switch (paramType.getSort())
    {
    case 3: 
      return BYTE_TYPE;
    case 1: 
      return BOOLEAN_TYPE;
    case 4: 
      return SHORT_TYPE;
    case 2: 
      return CHARACTER_TYPE;
    case 5: 
      return INTEGER_TYPE;
    case 6: 
      return FLOAT_TYPE;
    case 7: 
      return LONG_TYPE;
    case 8: 
      return DOUBLE_TYPE;
    }
    return paramType;
  }
  
  public void box(Type paramType)
  {
    if ((paramType.getSort() == 10) || (paramType.getSort() == 9)) {
      return;
    }
    if (paramType == Type.VOID_TYPE)
    {
      push((String)null);
    }
    else
    {
      Type localType = getBoxedType(paramType);
      newInstance(localType);
      if (paramType.getSize() == 2)
      {
        dupX2();
        dupX2();
        pop();
      }
      else
      {
        dupX1();
        swap();
      }
      invokeConstructor(localType, new Method("<init>", Type.VOID_TYPE, new Type[] { paramType }));
    }
  }
  
  public void valueOf(Type paramType)
  {
    if ((paramType.getSort() == 10) || (paramType.getSort() == 9)) {
      return;
    }
    if (paramType == Type.VOID_TYPE)
    {
      push((String)null);
    }
    else
    {
      Type localType = getBoxedType(paramType);
      invokeStatic(localType, new Method("valueOf", localType, new Type[] { paramType }));
    }
  }
  
  public void unbox(Type paramType)
  {
    Type localType = NUMBER_TYPE;
    Method localMethod = null;
    switch (paramType.getSort())
    {
    case 0: 
      return;
    case 2: 
      localType = CHARACTER_TYPE;
      localMethod = CHAR_VALUE;
      break;
    case 1: 
      localType = BOOLEAN_TYPE;
      localMethod = BOOLEAN_VALUE;
      break;
    case 8: 
      localMethod = DOUBLE_VALUE;
      break;
    case 6: 
      localMethod = FLOAT_VALUE;
      break;
    case 7: 
      localMethod = LONG_VALUE;
      break;
    case 3: 
    case 4: 
    case 5: 
      localMethod = INT_VALUE;
    }
    if (localMethod == null)
    {
      checkCast(paramType);
    }
    else
    {
      checkCast(localType);
      invokeVirtual(localType, localMethod);
    }
  }
  
  public Label newLabel()
  {
    return new Label();
  }
  
  public void mark(Label paramLabel)
  {
    mv.visitLabel(paramLabel);
  }
  
  public Label mark()
  {
    Label localLabel = new Label();
    mv.visitLabel(localLabel);
    return localLabel;
  }
  
  public void ifCmp(Type paramType, int paramInt, Label paramLabel)
  {
    switch (paramType.getSort())
    {
    case 7: 
      mv.visitInsn(148);
      break;
    case 8: 
      mv.visitInsn((paramInt == 156) || (paramInt == 157) ? 151 : 152);
      break;
    case 6: 
      mv.visitInsn((paramInt == 156) || (paramInt == 157) ? 149 : 150);
      break;
    case 9: 
    case 10: 
      switch (paramInt)
      {
      case 153: 
        mv.visitJumpInsn(165, paramLabel);
        return;
      case 154: 
        mv.visitJumpInsn(166, paramLabel);
        return;
      }
      throw new IllegalArgumentException("Bad comparison for type " + paramType);
    default: 
      int i = -1;
      switch (paramInt)
      {
      case 153: 
        i = 159;
        break;
      case 154: 
        i = 160;
        break;
      case 156: 
        i = 162;
        break;
      case 155: 
        i = 161;
        break;
      case 158: 
        i = 164;
        break;
      case 157: 
        i = 163;
      }
      mv.visitJumpInsn(i, paramLabel);
      return;
    }
    mv.visitJumpInsn(paramInt, paramLabel);
  }
  
  public void ifICmp(int paramInt, Label paramLabel)
  {
    ifCmp(Type.INT_TYPE, paramInt, paramLabel);
  }
  
  public void ifZCmp(int paramInt, Label paramLabel)
  {
    mv.visitJumpInsn(paramInt, paramLabel);
  }
  
  public void ifNull(Label paramLabel)
  {
    mv.visitJumpInsn(198, paramLabel);
  }
  
  public void ifNonNull(Label paramLabel)
  {
    mv.visitJumpInsn(199, paramLabel);
  }
  
  public void goTo(Label paramLabel)
  {
    mv.visitJumpInsn(167, paramLabel);
  }
  
  public void ret(int paramInt)
  {
    mv.visitVarInsn(169, paramInt);
  }
  
  public void tableSwitch(int[] paramArrayOfInt, TableSwitchGenerator paramTableSwitchGenerator)
  {
    float f;
    if (paramArrayOfInt.length == 0) {
      f = 0.0F;
    } else {
      f = paramArrayOfInt.length / (paramArrayOfInt[(paramArrayOfInt.length - 1)] - paramArrayOfInt[0] + 1);
    }
    tableSwitch(paramArrayOfInt, paramTableSwitchGenerator, f >= 0.5F);
  }
  
  public void tableSwitch(int[] paramArrayOfInt, TableSwitchGenerator paramTableSwitchGenerator, boolean paramBoolean)
  {
    for (int i = 1; i < paramArrayOfInt.length; i++) {
      if (paramArrayOfInt[i] < paramArrayOfInt[(i - 1)]) {
        throw new IllegalArgumentException("keys must be sorted ascending");
      }
    }
    Label localLabel1 = newLabel();
    Label localLabel2 = newLabel();
    if (paramArrayOfInt.length > 0)
    {
      int j = paramArrayOfInt.length;
      int k = paramArrayOfInt[0];
      int m = paramArrayOfInt[(j - 1)];
      int n = m - k + 1;
      Label[] arrayOfLabel;
      int i1;
      if (paramBoolean)
      {
        arrayOfLabel = new Label[n];
        Arrays.fill(arrayOfLabel, localLabel1);
        for (i1 = 0; i1 < j; i1++) {
          arrayOfLabel[(paramArrayOfInt[i1] - k)] = newLabel();
        }
        mv.visitTableSwitchInsn(k, m, localLabel1, arrayOfLabel);
        for (i1 = 0; i1 < n; i1++)
        {
          Label localLabel3 = arrayOfLabel[i1];
          if (localLabel3 != localLabel1)
          {
            mark(localLabel3);
            paramTableSwitchGenerator.generateCase(i1 + k, localLabel2);
          }
        }
      }
      else
      {
        arrayOfLabel = new Label[j];
        for (i1 = 0; i1 < j; i1++) {
          arrayOfLabel[i1] = newLabel();
        }
        mv.visitLookupSwitchInsn(localLabel1, paramArrayOfInt, arrayOfLabel);
        for (i1 = 0; i1 < j; i1++)
        {
          mark(arrayOfLabel[i1]);
          paramTableSwitchGenerator.generateCase(paramArrayOfInt[i1], localLabel2);
        }
      }
    }
    mark(localLabel1);
    paramTableSwitchGenerator.generateDefault();
    mark(localLabel2);
  }
  
  public void returnValue()
  {
    mv.visitInsn(returnType.getOpcode(172));
  }
  
  private void fieldInsn(int paramInt, Type paramType1, String paramString, Type paramType2)
  {
    mv.visitFieldInsn(paramInt, paramType1.getInternalName(), paramString, paramType2.getDescriptor());
  }
  
  public void getStatic(Type paramType1, String paramString, Type paramType2)
  {
    fieldInsn(178, paramType1, paramString, paramType2);
  }
  
  public void putStatic(Type paramType1, String paramString, Type paramType2)
  {
    fieldInsn(179, paramType1, paramString, paramType2);
  }
  
  public void getField(Type paramType1, String paramString, Type paramType2)
  {
    fieldInsn(180, paramType1, paramString, paramType2);
  }
  
  public void putField(Type paramType1, String paramString, Type paramType2)
  {
    fieldInsn(181, paramType1, paramString, paramType2);
  }
  
  private void invokeInsn(int paramInt, Type paramType, Method paramMethod, boolean paramBoolean)
  {
    String str = paramType.getSort() == 9 ? paramType.getDescriptor() : paramType.getInternalName();
    mv.visitMethodInsn(paramInt, str, paramMethod.getName(), paramMethod.getDescriptor(), paramBoolean);
  }
  
  public void invokeVirtual(Type paramType, Method paramMethod)
  {
    invokeInsn(182, paramType, paramMethod, false);
  }
  
  public void invokeConstructor(Type paramType, Method paramMethod)
  {
    invokeInsn(183, paramType, paramMethod, false);
  }
  
  public void invokeStatic(Type paramType, Method paramMethod)
  {
    invokeInsn(184, paramType, paramMethod, false);
  }
  
  public void invokeInterface(Type paramType, Method paramMethod)
  {
    invokeInsn(185, paramType, paramMethod, true);
  }
  
  public void invokeDynamic(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs)
  {
    mv.visitInvokeDynamicInsn(paramString1, paramString2, paramHandle, paramVarArgs);
  }
  
  private void typeInsn(int paramInt, Type paramType)
  {
    mv.visitTypeInsn(paramInt, paramType.getInternalName());
  }
  
  public void newInstance(Type paramType)
  {
    typeInsn(187, paramType);
  }
  
  public void newArray(Type paramType)
  {
    int i;
    switch (paramType.getSort())
    {
    case 1: 
      i = 4;
      break;
    case 2: 
      i = 5;
      break;
    case 3: 
      i = 8;
      break;
    case 4: 
      i = 9;
      break;
    case 5: 
      i = 10;
      break;
    case 6: 
      i = 6;
      break;
    case 7: 
      i = 11;
      break;
    case 8: 
      i = 7;
      break;
    default: 
      typeInsn(189, paramType);
      return;
    }
    mv.visitIntInsn(188, i);
  }
  
  public void arrayLength()
  {
    mv.visitInsn(190);
  }
  
  public void throwException()
  {
    mv.visitInsn(191);
  }
  
  public void throwException(Type paramType, String paramString)
  {
    newInstance(paramType);
    dup();
    push(paramString);
    invokeConstructor(paramType, Method.getMethod("void <init> (String)"));
    throwException();
  }
  
  public void checkCast(Type paramType)
  {
    if (!paramType.equals(OBJECT_TYPE)) {
      typeInsn(192, paramType);
    }
  }
  
  public void instanceOf(Type paramType)
  {
    typeInsn(193, paramType);
  }
  
  public void monitorEnter()
  {
    mv.visitInsn(194);
  }
  
  public void monitorExit()
  {
    mv.visitInsn(195);
  }
  
  public void endMethod()
  {
    if ((access & 0x400) == 0) {
      mv.visitMaxs(0, 0);
    }
    mv.visitEnd();
  }
  
  public void catchException(Label paramLabel1, Label paramLabel2, Type paramType)
  {
    Label localLabel = new Label();
    if (paramType == null) {
      mv.visitTryCatchBlock(paramLabel1, paramLabel2, localLabel, null);
    } else {
      mv.visitTryCatchBlock(paramLabel1, paramLabel2, localLabel, paramType.getInternalName());
    }
    mark(localLabel);
  }
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\jdk\internal\org\objectweb\asm\commons\GeneratorAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */