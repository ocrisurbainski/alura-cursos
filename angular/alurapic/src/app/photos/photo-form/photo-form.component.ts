import { HttpEvent, HttpEventType } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { UserService } from 'src/app/core/user/user.service';
import { AlertService } from 'src/app/shared/components/alert/alert.service';
import { PhotoService } from '../photo/photo.service';

@Component({
    selector: 'app-photo-form',
    templateUrl: './photo-form.component.html',
    styles: [
    ]
})
export class PhotoFormComponent implements OnInit {

    public formGroup: FormGroup;
    public preview: string = '';
    public percentUploadDone: number = 0;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private photoService: PhotoService,
        private alertService: AlertService,
        private userService: UserService) { }

    ngOnInit(): void {
        this.formGroup = this.formBuilder.group({
            file: ['', Validators.required],
            description: ['', Validators.maxLength(300)],
            allowComments: [true],
        });
    }

    upload(): void {
        const dados = this.formGroup.getRawValue();
        this.photoService.upload(dados.description, dados.allowComments, this.preview)
            .pipe(finalize(() => {
                this.router.navigate(['/user', this.userService.getUserName()]);
            }))
            .subscribe({
                next: (event: HttpEvent<any>) =>  {
                    if (event.type === HttpEventType.UploadProgress) {
                        const total: number = event.total === undefined ? 0 : event.total;
                        this.percentUploadDone = Math.round(100 * event.loaded / total);
                    } else if (event.type === HttpEventType.Response) {
                        this.alertService.success('Upload complete', true);
                    }
                }, 
                error: (err) => {
                    console.log(err);
                    this.alertService.danger('Upload photo error');
                }
            });
    }

    onChangeFile(event: any): void {
        const file = event.target.files[0];
        this.fileToBase64(file);
    }

    fileToBase64(file: File): void {
        const me = this, fileReader = new FileReader();

        fileReader.readAsBinaryString(file);
        fileReader.onload = function(event) {
            const base64 = btoa(event.target?.result as string);
            me.preview = `data:image/png;base64,${base64}`;
        };
        fileReader.onerror = function(err) {
            console.log(err);
        };
    }

}
