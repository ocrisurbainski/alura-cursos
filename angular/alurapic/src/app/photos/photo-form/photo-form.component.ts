import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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

    constructor(
        private formBuilder: FormBuilder,
        private photoService: PhotoService,
        private router: Router) { }

    ngOnInit(): void {
        this.formGroup = this.formBuilder.group({
            file: ['', Validators.required],
            description: ['', Validators.maxLength(300)],
            allowComments: [true],
        });
    }

    upload(): void {
        const dados = this.formGroup.getRawValue();
        this.photoService.upload(dados.description, dados.allowComments, this.preview).subscribe(() => this.router.navigate(['']));
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
            me.preview = base64;
        };
        fileReader.onerror = function(err) {
            console.log(err);
        };
    }

}
