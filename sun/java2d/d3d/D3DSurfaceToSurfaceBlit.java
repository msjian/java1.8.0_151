package sun.java2d.d3d;

import java.awt.Composite;
import sun.java2d.SurfaceData;
import sun.java2d.loops.Blit;
import sun.java2d.loops.CompositeType;
import sun.java2d.pipe.Region;

class D3DSurfaceToSurfaceBlit
  extends Blit
{
  D3DSurfaceToSurfaceBlit()
  {
    super(D3DSurfaceData.D3DSurface, CompositeType.AnyAlpha, D3DSurfaceData.D3DSurface);
  }
  
  public void Blit(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    D3DBlitLoops.IsoBlit(paramSurfaceData1, paramSurfaceData2, null, null, paramComposite, paramRegion, null, 1, paramInt1, paramInt2, paramInt1 + paramInt5, paramInt2 + paramInt6, paramInt3, paramInt4, paramInt3 + paramInt5, paramInt4 + paramInt6, false);
  }
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\sun\java2d\d3d\D3DSurfaceToSurfaceBlit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */