import {router} from "@/main";

export function splitImages(images, folderName){
    if (images == null) return ''
    let prefix = ''
    if ((router.currentRoute.path.match(/\//g)).length > 1)
        prefix = '../'
    const split = images.split(";")
    return split ? prefix + folderName + '/' + split[0] : ''
}