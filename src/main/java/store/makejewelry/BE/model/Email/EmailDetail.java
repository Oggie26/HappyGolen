package store.makejewelry.BE.model.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetail {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
    private String button;
    private String link;
    private String fullName;
}
