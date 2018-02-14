package localapp.zingohotels.com.localapp.Model;

/**
 * Created by CSC on 11/4/2017.
 */

public class Document1 {
    private int DocumentId;

    public int getProfileId() {
        return ProfileId;
    }

    public void setProfileId(int profileId) {
        ProfileId = profileId;
    }

    private int ProfileId;
    public int getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(int documentId) {
        DocumentId = documentId;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        DocumentNumber = documentNumber;
    }

    public String getDocumentName() {
        return DocumentName;
    }

    public void setDocumentName(String documentName) {
        DocumentName = documentName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getReEnterDocumentNumber() {
        return ReEnterDocumentNumber;
    }

    public void setReEnterDocumentNumber(String reEnterDocumentNumber) {
        ReEnterDocumentNumber = reEnterDocumentNumber;
    }

    private String DocumentType;
    private String DocumentNumber;
    private String DocumentName;
    private String Status;
    private String ReEnterDocumentNumber;
}
