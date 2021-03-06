package me.ercadio;



public class SubscribeCommand extends TypedCommand {
	private static StubDevice sd;
	public SubscribeCommand(TCPServer server){
		super(server);
		this.call = "/subscribe";
		sd = new StubDevice();
	}
	@Override
	public void run (String[] arg, Connection invoker){
		if(arg.length > 1){
			if(arg[1].equalsIgnoreCase("temperature")){
				while(true){
					String hex = sd.data.poll();
					if(hex != null){
						invoker.sendPacket("[Temperature] " + hex);
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			else if(arg[1].equalsIgnoreCase("moisture")){
				while(true){
					String hex = sd.data.poll();
					if(hex != null){
						invoker.sendPacket("[Moisture]" + hex);
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			else{
				invoker.sendPacket(this.call + " [device] <representation>\nAvailable Devices:\nTemperature\nMoisture\nAvailable Representations:\nHex\n");
			}
		}
	}
}
