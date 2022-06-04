package online.guessersoftware.casadoagricultorapi.webservice.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.StorageOptions;

@Service
public class StorageService {

	private static final String COTATIONS_BUCKET = "casa-do-agricultor-cotations-files";

	public void testStorage() {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		String bucketName = COTATIONS_BUCKET;
		String directory = "2022/";
		Page<Blob> blobs = storage.list(bucketName, BlobListOption.currentDirectory(), BlobListOption.prefix(directory));
		Iterator<Blob> blobIterator = blobs.iterateAll().iterator();
		while (blobIterator.hasNext()) {
			Blob blob = blobIterator.next();
			System.out.println(blob.getName());
		}
	}

	public void upload(InputStream is, String fileName, String directory) throws IOException {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		BlobId blobId = BlobId.of(COTATIONS_BUCKET, directory + fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		storage.createFrom(blobInfo, is);
	}

	public PDDocument getPDF(String fileFullNameOnBucket) throws IOException {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		BlobId blobId = BlobId.of(COTATIONS_BUCKET, fileFullNameOnBucket);
		Blob blob = storage.get(blobId);
		if (blob == null) {
			return null;
		}
		InputStream myInputStream = new ByteArrayInputStream(blob.getContent());
		BufferedInputStream fileParse = new BufferedInputStream(myInputStream);
		PDDocument document = PDDocument.load(fileParse);
		return document;
	}

}
