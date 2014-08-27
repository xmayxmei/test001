program Project1;

uses
  Forms,
  Unit1 in 'Unit1.pas' {Form1},
  T2SDK in '..\include\T2SDK.pas',
  tag_def in '..\include\tag_def.pas';

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.
