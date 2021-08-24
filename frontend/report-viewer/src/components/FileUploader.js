import React, { useState } from "react";

function FileUploader() {
    const API_URL = "http://localhost:8080/reports/";
    const [selectedFile, setSelectedFile] = useState();

    const changeHandler = (event) => {
        setSelectedFile(event.target.files[0]);
    };

    const handleSubmission = (API_TYPE) => {
        const formData = new FormData();
        formData.append('File', selectedFile);

        fetch(API_URL + API_TYPE,
            {
                method: 'POST',
                body: formData,
            }
        )
            .then((response) => response.json())
            .then((result) => {
                console.log('Success:', result);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    const UploadListings = () => {
        let API_TYPE = "UploadListings";
        handleSubmission(API_TYPE);
    }

    const UploadContacts = () => {
        let API_TYPE = "UploadContactListings";
        handleSubmission(API_TYPE);
    }

    return (
        <div className="input-group" >
            <div className="input-group file-uploader" >
                <span><b>Upload Listings</b></span>
                <input className="form-control" type="file" name="file" onChange={changeHandler} />
                <div>
                    <button className="form-control" onClick={UploadListings}>Submit</button>
                </div>
            </div>
            <div className="input-group file-uploader" >
                <span><b>Upload Contacts</b></span>
                <input className="form-control" type="file" name="file" onChange={changeHandler} />
                <div>
                    <button className="form-control" onClick={UploadContacts}>Submit</button>
                </div>
            </div>
        </div>
    )
}
export default FileUploader;