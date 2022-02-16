import { ValidatorFn, FormGroup } from "@angular/forms";

export const userNamePassword: ValidatorFn = (formGroup: FormGroup) => {
    const userNameControl = formGroup.get('userName');
    const passwordControl = formGroup.get('password');
    const userName = userNameControl ? userNameControl.value : undefined;
    const password = passwordControl ? passwordControl.value : undefined;

    if ((userName !== undefined && userName.trim().length > 0) || (password !== undefined && password.trim().length > 0)) {
        
        return userName !== password ? null : { userNamePassword: true };
    }

    return null;
};