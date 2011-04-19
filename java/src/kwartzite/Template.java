package kwartzite;

import java.util.Map;

public interface Template {

    public String render(Map<String, Object> context);

}