package online.guessersoftware.casadoagricultorapi.webservice.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Estou rodando pelo Quartz às: " + System.currentTimeMillis());
	}

}
