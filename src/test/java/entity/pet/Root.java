package entity.pet;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class Root {
    public long id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<Tag> tags;
    public String status;
}
