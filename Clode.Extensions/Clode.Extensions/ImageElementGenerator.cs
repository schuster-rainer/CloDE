using ICSharpCode.AvalonEdit.Document;
using ICSharpCode.AvalonEdit.Rendering;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows.Controls;
using System.Windows.Media.Imaging;

namespace Clode.Extensions
{
    public class ImageElementGenerator : VisualLineElementGenerator
    {
        readonly static Regex imageRegex = new Regex(@"#img ""([\.\/\w\d]+)""",
                                                     RegexOptions.IgnoreCase);
        readonly string basePath;

        public ImageElementGenerator(string basePath)
        {
            if (basePath == null)
                throw new ArgumentNullException("basePath");
            this.basePath = basePath;
        }

        Match FindMatch(int startOffset)
        {
            // fetch the end offset of the VisualLine being generated
            int endOffset = CurrentContext.VisualLine.LastDocumentLine.EndOffset;
            TextDocument document = CurrentContext.Document;
            string relevantText = document.GetText(startOffset, endOffset - startOffset);
            return imageRegex.Match(relevantText);
        }

        /// Gets the first offset >= startOffset where the generator wants to construct
        /// an element.
        /// Return -1 to signal no interest.
        public override int GetFirstInterestedOffset(int startOffset)
        {
            Match m = FindMatch(startOffset);
            return m.Success ? (startOffset + m.Index) : -1;
        }

        /// Constructs an element at the specified offset.
        /// May return null if no element should be constructed.
        public override VisualLineElement ConstructElement(int offset)
        {
            Match m = FindMatch(offset);
            // check whether there's a match exactly at offset
            if (m.Success && m.Index == 0)
            {
                BitmapImage bitmap = LoadBitmap(m.Groups[1].Value);
                if (bitmap != null)
                {
                    Image image = new Image();
                    image.Source = bitmap;
                    image.Width = bitmap.PixelWidth;
                    image.Height = bitmap.PixelHeight;
                    // Pass the length of the match to the 'documentLength' parameter
                    // of InlineObjectElement.
                    return new InlineObjectElement(m.Length, image);
                }
            }
            return null;
        }

        BitmapImage LoadBitmap(string fileName)
        {
            // TODO: add some kind of cache to avoid reloading the image whenever the
            // VisualLine is reconstructed
            try
            {
                string fullFileName = Path.Combine(basePath, fileName);
                if (File.Exists(fullFileName))
                {
                    BitmapImage bitmap = new BitmapImage(new Uri(fullFileName));
                    bitmap.Freeze();
                    return bitmap;
                }
            }
            catch (ArgumentException)
            {
                // invalid filename syntax
            }
            catch (IOException)
            {
                // other IO error
            }
            return null;
        }
    }
}
