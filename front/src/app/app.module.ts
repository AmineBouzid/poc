import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { TimeComponent } from './time/time.component';
import { ProfileComponent } from './profile/profile.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

import { UserAppComponent } from './user-app/user-app.component';
import { ManagerAppComponent } from './manager-app/manager-app.component';
import { AdminAppComponent } from './admin-app/admin-app.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { authInterceptorProviders } from '../_helpers/auth.interceptor';
import { ReactiveFormsModule } from '@angular/forms';
import { HomeComponent } from './home/home.component';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

const Materials = [
  MatListModule,
  MatToolbarModule,
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatSidenavModule,
  MatIconModule,
  MatDatepickerModule,
  MatNativeDateModule
];

@NgModule({  
  declarations: [
    AppComponent,
    TimeComponent,
    ProfileComponent,
    LoginComponent,
    RegisterComponent,
    UserAppComponent,
    ManagerAppComponent,
    AdminAppComponent,
    HomeComponent
  ],
  imports: [
    MatSnackBarModule,
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    FormsModule,
    HttpClientModule,
    Materials
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
