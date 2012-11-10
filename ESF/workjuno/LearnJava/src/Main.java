import java.awt.Rectangle;

import javax.swing.JTable;
import javax.swing.JViewport;

public class Main {
  public static void main(String[] argv) throws Exception {
    JTable table = new JTable(5, 5);

    int rowIndex = 1;
    int vColIndex = 2;
    scrollToCenter(table, rowIndex, vColIndex);
  }

  public static void scrollToCenter(JTable table, int rowIndex, int vColIndex) {
    if (!(table.getParent() instanceof JViewport)) {
      return;
    }
    JViewport viewport = (JViewport) table.getParent();
    Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);
    Rectangle viewRect = viewport.getViewRect();
    rect.setLocation(rect.x - viewRect.x, rect.y - viewRect.y);

    int centerX = (viewRect.width - rect.width) / 2;
    int centerY = (viewRect.height - rect.height) / 2;
    if (rect.x < centerX) {
      centerX = -centerX;
    }
    if (rect.y < centerY) {
      centerY = -centerY;
    }
    rect.translate(centerX, centerY);
    viewport.scrollRectToVisible(rect);
  }
}