
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WatchDir {

	public static void main(String[] args) throws IOException,
			InterruptedException {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYMMdd");
                String formattedDate = LocalDate.now().format(formatter);
                System.out.println(formattedDate);
            
		Path faxFolder = Paths.get("C:\\Sanjay");//("./fax/");
		WatchService watchService = FileSystems.getDefault().newWatchService();
		faxFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		boolean valid = true;
		do {
			WatchKey watchKey = watchService.take();

			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileName = event.context().toString();
					System.out.println("File Created:" + fileName);
					if(fileName.contains("FLTS"+formattedDate))
					{
						System.out.println("File arrived.");
//                                                System.exit(0);
                                                return;
					}
				}
			}
			valid = watchKey.reset();

		} while (valid);

	}
}