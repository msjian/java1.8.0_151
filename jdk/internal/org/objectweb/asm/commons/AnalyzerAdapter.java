package jdk.internal.org.objectweb.asm.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

public class AnalyzerAdapter
  extends MethodVisitor
{
  public List<Object> locals;
  public List<Object> stack;
  private List<Label> labels;
  public Map<Object, Object> uninitializedTypes;
  private int maxStack;
  private int maxLocals;
  private String owner;
  
  public AnalyzerAdapter(String paramString1, int paramInt, String paramString2, String paramString3, MethodVisitor paramMethodVisitor)
  {
    this(327680, paramString1, paramInt, paramString2, paramString3, paramMethodVisitor);
    if (getClass() != AnalyzerAdapter.class) {
      throw new IllegalStateException();
    }
  }
  
  protected AnalyzerAdapter(int paramInt1, String paramString1, int paramInt2, String paramString2, String paramString3, MethodVisitor paramMethodVisitor)
  {
    super(paramInt1, paramMethodVisitor);
    owner = paramString1;
    locals = new ArrayList();
    stack = new ArrayList();
    uninitializedTypes = new HashMap();
    if ((paramInt2 & 0x8) == 0) {
      if ("<init>".equals(paramString2)) {
        locals.add(Opcodes.UNINITIALIZED_THIS);
      } else {
        locals.add(paramString1);
      }
    }
    Type[] arrayOfType = Type.getArgumentTypes(paramString3);
    for (int i = 0; i < arrayOfType.length; i++)
    {
      Type localType = arrayOfType[i];
      switch (localType.getSort())
      {
      case 1: 
      case 2: 
      case 3: 
      case 4: 
      case 5: 
        locals.add(Opcodes.INTEGER);
        break;
      case 6: 
        locals.add(Opcodes.FLOAT);
        break;
      case 7: 
        locals.add(Opcodes.LONG);
        locals.add(Opcodes.TOP);
        break;
      case 8: 
        locals.add(Opcodes.DOUBLE);
        locals.add(Opcodes.TOP);
        break;
      case 9: 
        locals.add(arrayOfType[i].getDescriptor());
        break;
      default: 
        locals.add(arrayOfType[i].getInternalName());
      }
    }
    maxLocals = locals.size();
  }
  
  public void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2)
  {
    if (paramInt1 != -1) {
      throw new IllegalStateException("ClassReader.accept() should be called with EXPAND_FRAMES flag");
    }
    if (mv != null) {
      mv.visitFrame(paramInt1, paramInt2, paramArrayOfObject1, paramInt3, paramArrayOfObject2);
    }
    if (locals != null)
    {
      locals.clear();
      stack.clear();
    }
    else
    {
      locals = new ArrayList();
      stack = new ArrayList();
    }
    visitFrameTypes(paramInt2, paramArrayOfObject1, locals);
    visitFrameTypes(paramInt3, paramArrayOfObject2, stack);
    maxStack = Math.max(maxStack, stack.size());
  }
  
  private static void visitFrameTypes(int paramInt, Object[] paramArrayOfObject, List<Object> paramList)
  {
    for (int i = 0; i < paramInt; i++)
    {
      Object localObject = paramArrayOfObject[i];
      paramList.add(localObject);
      if ((localObject == Opcodes.LONG) || (localObject == Opcodes.DOUBLE)) {
        paramList.add(Opcodes.TOP);
      }
    }
  }
  
  public void visitInsn(int paramInt)
  {
    if (mv != null) {
      mv.visitInsn(paramInt);
    }
    execute(paramInt, 0, null);
    if (((paramInt >= 172) && (paramInt <= 177)) || (paramInt == 191))
    {
      locals = null;
      stack = null;
    }
  }
  
  public void visitIntInsn(int paramInt1, int paramInt2)
  {
    if (mv != null) {
      mv.visitIntInsn(paramInt1, paramInt2);
    }
    execute(paramInt1, paramInt2, null);
  }
  
  public void visitVarInsn(int paramInt1, int paramInt2)
  {
    if (mv != null) {
      mv.visitVarInsn(paramInt1, paramInt2);
    }
    execute(paramInt1, paramInt2, null);
  }
  
  public void visitTypeInsn(int paramInt, String paramString)
  {
    if (paramInt == 187)
    {
      if (labels == null)
      {
        Label localLabel = new Label();
        labels = new ArrayList(3);
        labels.add(localLabel);
        if (mv != null) {
          mv.visitLabel(localLabel);
        }
      }
      for (int i = 0; i < labels.size(); i++) {
        uninitializedTypes.put(labels.get(i), paramString);
      }
    }
    if (mv != null) {
      mv.visitTypeInsn(paramInt, paramString);
    }
    execute(paramInt, 0, paramString);
  }
  
  public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    if (mv != null) {
      mv.visitFieldInsn(paramInt, paramString1, paramString2, paramString3);
    }
    execute(paramInt, 0, paramString3);
  }
  
  @Deprecated
  public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    if (api >= 327680)
    {
      super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
      return;
    }
    doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramInt == 185);
  }
  
  public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    if (api < 327680)
    {
      super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
      return;
    }
    doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
  }
  
  private void doVisitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    if (mv != null) {
      mv.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
    }
    if (locals == null)
    {
      labels = null;
      return;
    }
    pop(paramString3);
    if (paramInt != 184)
    {
      Object localObject1 = pop();
      if ((paramInt == 183) && (paramString2.charAt(0) == '<'))
      {
        Object localObject2;
        if (localObject1 == Opcodes.UNINITIALIZED_THIS) {
          localObject2 = owner;
        } else {
          localObject2 = uninitializedTypes.get(localObject1);
        }
        for (int i = 0; i < locals.size(); i++) {
          if (locals.get(i) == localObject1) {
            locals.set(i, localObject2);
          }
        }
        for (i = 0; i < stack.size(); i++) {
          if (stack.get(i) == localObject1) {
            stack.set(i, localObject2);
          }
        }
      }
    }
    pushDesc(paramString3);
    labels = null;
  }
  
  public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs)
  {
    if (mv != null) {
      mv.visitInvokeDynamicInsn(paramString1, paramString2, paramHandle, paramVarArgs);
    }
    if (locals == null)
    {
      labels = null;
      return;
    }
    pop(paramString2);
    pushDesc(paramString2);
    labels = null;
  }
  
  public void visitJumpInsn(int paramInt, Label paramLabel)
  {
    if (mv != null) {
      mv.visitJumpInsn(paramInt, paramLabel);
    }
    execute(paramInt, 0, null);
    if (paramInt == 167)
    {
      locals = null;
      stack = null;
    }
  }
  
  public void visitLabel(Label paramLabel)
  {
    if (mv != null) {
      mv.visitLabel(paramLabel);
    }
    if (labels == null) {
      labels = new ArrayList(3);
    }
    labels.add(paramLabel);
  }
  
  public void visitLdcInsn(Object paramObject)
  {
    if (mv != null) {
      mv.visitLdcInsn(paramObject);
    }
    if (locals == null)
    {
      labels = null;
      return;
    }
    if ((paramObject instanceof Integer))
    {
      push(Opcodes.INTEGER);
    }
    else if ((paramObject instanceof Long))
    {
      push(Opcodes.LONG);
      push(Opcodes.TOP);
    }
    else if ((paramObject instanceof Float))
    {
      push(Opcodes.FLOAT);
    }
    else if ((paramObject instanceof Double))
    {
      push(Opcodes.DOUBLE);
      push(Opcodes.TOP);
    }
    else if ((paramObject instanceof String))
    {
      push("java/lang/String");
    }
    else if ((paramObject instanceof Type))
    {
      int i = ((Type)paramObject).getSort();
      if ((i == 10) || (i == 9)) {
        push("java/lang/Class");
      } else if (i == 11) {
        push("java/lang/invoke/MethodType");
      } else {
        throw new IllegalArgumentException();
      }
    }
    else if ((paramObject instanceof Handle))
    {
      push("java/lang/invoke/MethodHandle");
    }
    else
    {
      throw new IllegalArgumentException();
    }
    labels = null;
  }
  
  public void visitIincInsn(int paramInt1, int paramInt2)
  {
    if (mv != null) {
      mv.visitIincInsn(paramInt1, paramInt2);
    }
    execute(132, paramInt1, null);
  }
  
  public void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs)
  {
    if (mv != null) {
      mv.visitTableSwitchInsn(paramInt1, paramInt2, paramLabel, paramVarArgs);
    }
    execute(170, 0, null);
    locals = null;
    stack = null;
  }
  
  public void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfInt, Label[] paramArrayOfLabel)
  {
    if (mv != null) {
      mv.visitLookupSwitchInsn(paramLabel, paramArrayOfInt, paramArrayOfLabel);
    }
    execute(171, 0, null);
    locals = null;
    stack = null;
  }
  
  public void visitMultiANewArrayInsn(String paramString, int paramInt)
  {
    if (mv != null) {
      mv.visitMultiANewArrayInsn(paramString, paramInt);
    }
    execute(197, paramInt, paramString);
  }
  
  public void visitMaxs(int paramInt1, int paramInt2)
  {
    if (mv != null)
    {
      maxStack = Math.max(maxStack, paramInt1);
      maxLocals = Math.max(maxLocals, paramInt2);
      mv.visitMaxs(maxStack, maxLocals);
    }
  }
  
  private Object get(int paramInt)
  {
    maxLocals = Math.max(maxLocals, paramInt + 1);
    return paramInt < locals.size() ? locals.get(paramInt) : Opcodes.TOP;
  }
  
  private void set(int paramInt, Object paramObject)
  {
    maxLocals = Math.max(maxLocals, paramInt + 1);
    while (paramInt >= locals.size()) {
      locals.add(Opcodes.TOP);
    }
    locals.set(paramInt, paramObject);
  }
  
  private void push(Object paramObject)
  {
    stack.add(paramObject);
    maxStack = Math.max(maxStack, stack.size());
  }
  
  private void pushDesc(String paramString)
  {
    int i = paramString.charAt(0) == '(' ? paramString.indexOf(')') + 1 : 0;
    switch (paramString.charAt(i))
    {
    case 'V': 
      
    case 'B': 
    case 'C': 
    case 'I': 
    case 'S': 
    case 'Z': 
      push(Opcodes.INTEGER);
      return;
    case 'F': 
      push(Opcodes.FLOAT);
      return;
    case 'J': 
      push(Opcodes.LONG);
      push(Opcodes.TOP);
      return;
    case 'D': 
      push(Opcodes.DOUBLE);
      push(Opcodes.TOP);
      return;
    case '[': 
      if (i == 0) {
        push(paramString);
      } else {
        push(paramString.substring(i, paramString.length()));
      }
      break;
    case 'E': 
    case 'G': 
    case 'H': 
    case 'K': 
    case 'L': 
    case 'M': 
    case 'N': 
    case 'O': 
    case 'P': 
    case 'Q': 
    case 'R': 
    case 'T': 
    case 'U': 
    case 'W': 
    case 'X': 
    case 'Y': 
    default: 
      if (i == 0) {
        push(paramString.substring(1, paramString.length() - 1));
      } else {
        push(paramString.substring(i + 1, paramString.length() - 1));
      }
      break;
    }
  }
  
  private Object pop()
  {
    return stack.remove(stack.size() - 1);
  }
  
  private void pop(int paramInt)
  {
    int i = stack.size();
    int j = i - paramInt;
    for (int k = i - 1; k >= j; k--) {
      stack.remove(k);
    }
  }
  
  private void pop(String paramString)
  {
    int i = paramString.charAt(0);
    if (i == 40)
    {
      int j = 0;
      Type[] arrayOfType = Type.getArgumentTypes(paramString);
      for (int k = 0; k < arrayOfType.length; k++) {
        j += arrayOfType[k].getSize();
      }
      pop(j);
    }
    else if ((i == 74) || (i == 68))
    {
      pop(2);
    }
    else
    {
      pop(1);
    }
  }
  
  private void execute(int paramInt1, int paramInt2, String paramString)
  {
    if (locals == null)
    {
      labels = null;
      return;
    }
    Object localObject1;
    Object localObject2;
    Object localObject3;
    switch (paramInt1)
    {
    case 0: 
    case 116: 
    case 117: 
    case 118: 
    case 119: 
    case 145: 
    case 146: 
    case 147: 
    case 167: 
    case 177: 
      break;
    case 1: 
      push(Opcodes.NULL);
      break;
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 7: 
    case 8: 
    case 16: 
    case 17: 
      push(Opcodes.INTEGER);
      break;
    case 9: 
    case 10: 
      push(Opcodes.LONG);
      push(Opcodes.TOP);
      break;
    case 11: 
    case 12: 
    case 13: 
      push(Opcodes.FLOAT);
      break;
    case 14: 
    case 15: 
      push(Opcodes.DOUBLE);
      push(Opcodes.TOP);
      break;
    case 21: 
    case 23: 
    case 25: 
      push(get(paramInt2));
      break;
    case 22: 
    case 24: 
      push(get(paramInt2));
      push(Opcodes.TOP);
      break;
    case 46: 
    case 51: 
    case 52: 
    case 53: 
      pop(2);
      push(Opcodes.INTEGER);
      break;
    case 47: 
    case 143: 
      pop(2);
      push(Opcodes.LONG);
      push(Opcodes.TOP);
      break;
    case 48: 
      pop(2);
      push(Opcodes.FLOAT);
      break;
    case 49: 
    case 138: 
      pop(2);
      push(Opcodes.DOUBLE);
      push(Opcodes.TOP);
      break;
    case 50: 
      pop(1);
      localObject1 = pop();
      if ((localObject1 instanceof String)) {
        pushDesc(((String)localObject1).substring(1));
      } else {
        push("java/lang/Object");
      }
      break;
    case 54: 
    case 56: 
    case 58: 
      localObject1 = pop();
      set(paramInt2, localObject1);
      if (paramInt2 > 0)
      {
        localObject2 = get(paramInt2 - 1);
        if ((localObject2 == Opcodes.LONG) || (localObject2 == Opcodes.DOUBLE)) {
          set(paramInt2 - 1, Opcodes.TOP);
        }
      }
      break;
    case 55: 
    case 57: 
      pop(1);
      localObject1 = pop();
      set(paramInt2, localObject1);
      set(paramInt2 + 1, Opcodes.TOP);
      if (paramInt2 > 0)
      {
        localObject2 = get(paramInt2 - 1);
        if ((localObject2 == Opcodes.LONG) || (localObject2 == Opcodes.DOUBLE)) {
          set(paramInt2 - 1, Opcodes.TOP);
        }
      }
      break;
    case 79: 
    case 81: 
    case 83: 
    case 84: 
    case 85: 
    case 86: 
      pop(3);
      break;
    case 80: 
    case 82: 
      pop(4);
      break;
    case 87: 
    case 153: 
    case 154: 
    case 155: 
    case 156: 
    case 157: 
    case 158: 
    case 170: 
    case 171: 
    case 172: 
    case 174: 
    case 176: 
    case 191: 
    case 194: 
    case 195: 
    case 198: 
    case 199: 
      pop(1);
      break;
    case 88: 
    case 159: 
    case 160: 
    case 161: 
    case 162: 
    case 163: 
    case 164: 
    case 165: 
    case 166: 
    case 173: 
    case 175: 
      pop(2);
      break;
    case 89: 
      localObject1 = pop();
      push(localObject1);
      push(localObject1);
      break;
    case 90: 
      localObject1 = pop();
      localObject2 = pop();
      push(localObject1);
      push(localObject2);
      push(localObject1);
      break;
    case 91: 
      localObject1 = pop();
      localObject2 = pop();
      localObject3 = pop();
      push(localObject1);
      push(localObject3);
      push(localObject2);
      push(localObject1);
      break;
    case 92: 
      localObject1 = pop();
      localObject2 = pop();
      push(localObject2);
      push(localObject1);
      push(localObject2);
      push(localObject1);
      break;
    case 93: 
      localObject1 = pop();
      localObject2 = pop();
      localObject3 = pop();
      push(localObject2);
      push(localObject1);
      push(localObject3);
      push(localObject2);
      push(localObject1);
      break;
    case 94: 
      localObject1 = pop();
      localObject2 = pop();
      localObject3 = pop();
      Object localObject4 = pop();
      push(localObject2);
      push(localObject1);
      push(localObject4);
      push(localObject3);
      push(localObject2);
      push(localObject1);
      break;
    case 95: 
      localObject1 = pop();
      localObject2 = pop();
      push(localObject1);
      push(localObject2);
      break;
    case 96: 
    case 100: 
    case 104: 
    case 108: 
    case 112: 
    case 120: 
    case 122: 
    case 124: 
    case 126: 
    case 128: 
    case 130: 
    case 136: 
    case 142: 
    case 149: 
    case 150: 
      pop(2);
      push(Opcodes.INTEGER);
      break;
    case 97: 
    case 101: 
    case 105: 
    case 109: 
    case 113: 
    case 127: 
    case 129: 
    case 131: 
      pop(4);
      push(Opcodes.LONG);
      push(Opcodes.TOP);
      break;
    case 98: 
    case 102: 
    case 106: 
    case 110: 
    case 114: 
    case 137: 
    case 144: 
      pop(2);
      push(Opcodes.FLOAT);
      break;
    case 99: 
    case 103: 
    case 107: 
    case 111: 
    case 115: 
      pop(4);
      push(Opcodes.DOUBLE);
      push(Opcodes.TOP);
      break;
    case 121: 
    case 123: 
    case 125: 
      pop(3);
      push(Opcodes.LONG);
      push(Opcodes.TOP);
      break;
    case 132: 
      set(paramInt2, Opcodes.INTEGER);
      break;
    case 133: 
    case 140: 
      pop(1);
      push(Opcodes.LONG);
      push(Opcodes.TOP);
      break;
    case 134: 
      pop(1);
      push(Opcodes.FLOAT);
      break;
    case 135: 
    case 141: 
      pop(1);
      push(Opcodes.DOUBLE);
      push(Opcodes.TOP);
      break;
    case 139: 
    case 190: 
    case 193: 
      pop(1);
      push(Opcodes.INTEGER);
      break;
    case 148: 
    case 151: 
    case 152: 
      pop(4);
      push(Opcodes.INTEGER);
      break;
    case 168: 
    case 169: 
      throw new RuntimeException("JSR/RET are not supported");
    case 178: 
      pushDesc(paramString);
      break;
    case 179: 
      pop(paramString);
      break;
    case 180: 
      pop(1);
      pushDesc(paramString);
      break;
    case 181: 
      pop(paramString);
      pop();
      break;
    case 187: 
      push(labels.get(0));
      break;
    case 188: 
      pop();
      switch (paramInt2)
      {
      case 4: 
        pushDesc("[Z");
        break;
      case 5: 
        pushDesc("[C");
        break;
      case 8: 
        pushDesc("[B");
        break;
      case 9: 
        pushDesc("[S");
        break;
      case 10: 
        pushDesc("[I");
        break;
      case 6: 
        pushDesc("[F");
        break;
      case 7: 
        pushDesc("[D");
        break;
      default: 
        pushDesc("[J");
      }
      break;
    case 189: 
      pop();
      pushDesc("[" + Type.getObjectType(paramString));
      break;
    case 192: 
      pop();
      pushDesc(Type.getObjectType(paramString).getDescriptor());
      break;
    case 18: 
    case 19: 
    case 20: 
    case 26: 
    case 27: 
    case 28: 
    case 29: 
    case 30: 
    case 31: 
    case 32: 
    case 33: 
    case 34: 
    case 35: 
    case 36: 
    case 37: 
    case 38: 
    case 39: 
    case 40: 
    case 41: 
    case 42: 
    case 43: 
    case 44: 
    case 45: 
    case 59: 
    case 60: 
    case 61: 
    case 62: 
    case 63: 
    case 64: 
    case 65: 
    case 66: 
    case 67: 
    case 68: 
    case 69: 
    case 70: 
    case 71: 
    case 72: 
    case 73: 
    case 74: 
    case 75: 
    case 76: 
    case 77: 
    case 78: 
    case 182: 
    case 183: 
    case 184: 
    case 185: 
    case 186: 
    case 196: 
    case 197: 
    default: 
      pop(paramInt2);
      pushDesc(paramString);
    }
    labels = null;
  }
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\jdk\internal\org\objectweb\asm\commons\AnalyzerAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */