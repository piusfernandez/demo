package com.example.demo;

import java.io.IOException;
import java.util.concurrent.CancellationException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestOperations;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@WebMvcTest
public class RestClientControllerTest {

	@Autowired
	PoolingHttpClientConnectionManager connectionManager;
	
	@Autowired
	RestOperations restOperations;
	
	static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        System.setProperty("profile", "services");
    }
	
	@Test
	public void getVehicleForYearMakeModel()	{
		
		long startTime = System.nanoTime();

		HttpHost host = new HttpHost("http://localhost", 8080);
		HttpRoute route = new HttpRoute(host);
		HttpGet get = new HttpGet("http://localhost:8080/vehicle/1981/BMW/R100CS");
		
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		for(int i = 0; i < 5; i++){
			CloseableHttpClient client 
			  = HttpClients.custom().setConnectionManager(connectionManager).build();
					
			
			MultiHttpClientConnThread thread = new MultiHttpClientConnThread(client, get);
			thread.setName("thread:" + i);
			thread.start();
			/*try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			System.out.println("invoke time " + i + " : " + formatStats(route, thread));

			//restOperations.getForObject(urlYearMakeMode, List.class, year, make, model);
		}
		
        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("total time: " + totalTime);
		
	}
	
	class MultiHttpClientConnThread extends Thread {
	    private CloseableHttpClient client;
	    private HttpGet get;

	    public MultiHttpClientConnThread(CloseableHttpClient client, HttpGet get) {
			this.client = client;
			this.get = get;
		}
		public void run(){
	        try {
	        	System.out.println("Invoking http client");	        	
	            HttpResponse response = client.execute(get);  
	            //EntityUtils.consume(response.getEntity());
	            //Thread.sleep(1000);
	            System.out.println("response from " + this.getName() + ":" + EntityUtils.toString(response.getEntity()));
	        } catch (ClientProtocolException ex) {    
	        	ex.printStackTrace();
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        } catch (CancellationException e) {
	        	System.out.println("cancel exception caught " + e.getMessage());
	        	e.printStackTrace();
	        }catch (Exception e){
	        	e.printStackTrace();
	        }
	    }
	}
	

	private String formatStats(final HttpRoute route, MultiHttpClientConnThread thread) {
	    final StringBuilder buf = new StringBuilder();
	    final PoolStats totals = this.connectionManager.getTotalStats();
	    final PoolStats stats = this.connectionManager.getStats(route);
	    buf.append(thread.getName()).append("; ");
	    buf.append("[total kept alive: ").append(totals.getAvailable()).append("; ");
	    buf.append("route allocated: ").append(stats.getLeased() + stats.getAvailable());
	    buf.append(" of ").append(stats.getMax()).append("; ");
	    buf.append("total allocated: ").append(totals.getLeased() + totals.getAvailable());
	    buf.append(" of ").append(totals.getMax()).append("]");
	    return buf.toString();
	}

}
