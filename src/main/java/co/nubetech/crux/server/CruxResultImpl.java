package co.nubetech.crux.server;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.apache.hadoop.hbase.client.Result;

import co.nubetech.crux.model.Alias;
import co.nubetech.crux.model.Report;
import co.nubetech.crux.model.ReportDesign;
import co.nubetech.crux.server.functions.CruxFunction;
import co.nubetech.crux.util.CruxException;

/*
 * This class is used in case of a get result
 */
public class CruxResultImpl implements CruxResult{
	Result result;
	Report report;
	
	public CruxResultImpl(Result result, Report report){
		this.result = result;
		this.report = report;		
	}

	@Override
	public Object get(int index) throws CruxException{
		List<Stack<CruxFunction>> functions = report.getFunctions();
		if (report != null && result != null) {
			Collection<ReportDesign> designs = report.getDesigns();
			if (designs != null) {
				if (index < designs.size()) {
					ReportDesign design = report.getDesigns().get(index);
					Object val = ServerUtil.getObjectValue(result, ServerUtil.getAlias(design));
					return FunctionUtil.getResultByApplyingAllFunctions(val, functions.get(index));					
				}
			}
		}		
		return null;
	}	
	

}
