package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BaseRequest <T> {
    public int statusCode;
    public List<String> headers;
    public T jsonObject;
}
