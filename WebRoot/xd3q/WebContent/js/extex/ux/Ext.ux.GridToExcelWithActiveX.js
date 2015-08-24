/**/
/**
 * @author qinjinwei
 *
 * time: 2008-7-24 20:28:02
 */
var idTmr = "";
Ext.ux.Grid2Excel = {

    Save2Excel: function (grid) {
       
		try{
			var cm = grid.getColumnModel();
	        var store = grid.getStore();
	
	        var it = store.data.items;
	        var rows = it.length;
	        var oXL = new ActiveXObject("Excel.application");
	        var oWB = oXL.Workbooks.Add();
	        var oSheet = oWB.ActiveSheet;
	
	        oSheet.Rows(1).RowHeight = 38;
	        var iOffSet = 1;
	            
	        // J行 I列
	        for (var i = 0; i < cm.getColumnCount(); i++) {
	
	            if (cm.isHidden(i)) {
	              iOffSet--;
	              continue;
	            }
	            with (oSheet.Cells(2, i + iOffSet) ){
	              value = cm.getColumnHeader(i);
	              Font.Size = 10;
	              Font.bold = true;
	              Interior.ColorIndex=37;
	              HorizontalAlignment = 3;
	              Borders(1).LineStyle =1;
	              Borders(2).LineStyle =1;
	              Borders(3).LineStyle =1;
	              Borders(4).LineStyle =1;
					ColumnWidth = cm.getColumnWidth(i) / 7; // 宽度
	            }
	            
	            for (var j = 0; j < rows; j++) {
	                r = it[j].data;
	                var v = r[cm.getDataIndex(i)];
	                var fld = store.recordType.prototype.fields.get(cm.getDataIndex(i));
	                if (!fld) {
	                  continue;
	                }
	                if (fld.type == 'date') {
	                    v = v.format('Y-m-d');
	                } else if (fld.type == 'float'){
	                  oSheet.Cells(3 + j, i + iOffSet).NumberFormatLocal = "0.000";
	                } else {
	                  oSheet.Cells(3 + j, i + iOffSet).NumberFormatLocal = "@";
	                }
	                with (oSheet.Cells(3 + j, i + iOffSet)) {
	                  value = v;
	                  Font.Size = 10;
	                  Interior.ColorIndex=2;
	                  Borders(1).LineStyle =1;
	                  Borders(2).LineStyle =1;
	                  Borders(3).LineStyle =1;
	                  Borders(4).LineStyle =1;
	                }
	                
	            }
	        }
	                
	        oSheet.Cells(1, 1).value = grid.title;
	        oSheet.Cells(1, 1).Font.Size = 14;
	        oSheet.Cells(1, 1).Font.bold = true;
	        oSheet.Range("A1").HorizontalAlignment = 3; // 1合并居中 2左对齐 3居中
	        oSheet.Range("A1:Z1").Merge();
	        
	        oXL.DisplayAlerts = false;
	        oXL.Save();
	        oXL.DisplayAlerts = true;
	        oXL.Quit();
	        oXL = null;
	        idTmr = window.setInterval("Cleanup();", 1);
        }catch(e){
			alert("请确定已安装Excel2000(或更高版本)!,并将该网址添加到可信任站点");
			return;
		}
    }
};


function Cleanup() {
    window.clearInterval(idTmr);
    CollectGarbage();
};