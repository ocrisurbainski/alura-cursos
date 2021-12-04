import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { PhotoComponent } from './photo/photo.component';
import { PhotoService } from './photo/photo.service';

@NgModule({
    declarations: [
        PhotoComponent,
    ],
    imports: [
        CommonModule,
        HttpClientModule,
    ],
    exports: [
        PhotoComponent,
    ],
    providers: [
        PhotoService
    ]
})
export class PhotosModule { }
