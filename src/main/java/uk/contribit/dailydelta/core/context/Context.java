package uk.contribit.dailydelta.core.context;

public final class Context {
    private final String content;

    public Context(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Context)) return false;

        Context context = (Context) o;

        return content != null ? content.equals(context.content) : context.content == null;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Context{" +
                "content='" + content + '\'' +
                '}';
    }
}
