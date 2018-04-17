package javafx.extensions;

public abstract class DialogController {
    private DialogResult dialogResult = DialogResult.Cancel;

    public DialogResult getDialogResult() {
        return dialogResult;
    }

    public void setDialogResult(DialogResult dialogResult) {
        this.dialogResult = dialogResult;
    }
}
